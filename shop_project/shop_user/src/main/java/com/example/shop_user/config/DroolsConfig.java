package com.example.shop_user.config;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "rules/";

    @Bean
    public KieContainer kieContainer() throws IOException {
        KieServices kieServices = KieServices.Factory.get();

        // 1. 创建 KieFileSystem 并加载规则
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        Resource[] ruleFiles = getRuleFiles();

        if (ruleFiles.length == 0) {
            throw new IllegalStateException("No DRL files found in " + RULES_PATH);
        }

        for (Resource file : ruleFiles) {
            kieFileSystem.write(
                    ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
        }

        // 2. 使用 KieBuilder 构建
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        // 3. 验证构建结果
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Rule compilation errors: " + results.getMessages());
        }

        // 4. 创建 KieContainer（关键修正点）
        KieModule kieModule = kieBuilder.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    private Resource[] getRuleFiles() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources("classpath*:" + RULES_PATH + "**/*.drl");
    }
}
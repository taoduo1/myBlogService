package com.example.shop_es.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.ant;


/**
 * @author duo.tao
 * @Description: swagger配置类
 * @date 2022-06-13 23:14
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket createRestApi() {
        //增加认证头
        List<Parameter> parameters = new ArrayList<>();


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("com.example.shop_es"))
                //为任何接口生成API文档
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.and(ant("/**"), Predicates.not(ant("/error")), Predicates.not(ant("/management/**")), Predicates.not(ant("/management*"))))
                .build().globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("陶铎", "null", "taoduo007wow@outlook.com");
        return new ApiInfoBuilder()
                .title("my-project-es-api")
                .description("我的es項目接口")
                .contact(contact)
                .version("1.0")
                .build();
    }

    /**
     * 配置swagger2的静态资源路径
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
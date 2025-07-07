package com.example.shop_user.service.impl;

import com.example.shop_user.dto.Order;
import com.example.shop_user.dto.RuleResult;
import com.example.shop_user.service.DiscountRuleService;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.QueryResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DiscountRuleServiceImpl implements DiscountRuleService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountRuleServiceImpl.class);

    @Resource
    private KieContainer kieContainer;


    @Override
    public RuleResult applyDiscount(Order order) {
        // 1. 创建配置并指定伪时钟
        KieServices kieServices = KieServices.Factory.get();
        KieSessionConfiguration config = kieServices.newKieSessionConfiguration();
        config.setOption(ClockTypeOption.PSEUDO); // 关键设置

        // 2. 创建带配置的KieSession
        KieSession kieSession = kieContainer.newKieSession(config);

        try {
            // 3. 安全获取伪时钟（不再需要强制转换）
            SessionPseudoClock clock = kieSession.getSessionClock();

            // 设置全局变量
            //kieSession.setGlobal("logger", logger);

            // 插入事实
            kieSession.insert(order);
            kieSession.insert(clock);

            // 执行规则
            kieSession.fireAllRules();

            // 获取规则结果
            QueryResults results = kieSession.getQueryResults("DiscountResults");
            if (results.size() <= 0) return null;
            return (RuleResult) results.iterator().next().get("$result");
        } finally {
            kieSession.dispose();
        }

    }


}

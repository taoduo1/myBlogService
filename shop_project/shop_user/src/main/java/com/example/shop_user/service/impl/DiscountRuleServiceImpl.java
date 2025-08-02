package com.example.shop_user.service.impl;

import com.example.shop_user.dto.Order;
import com.example.shop_user.dto.RuleResult;
import com.example.shop_user.entity.User;
import com.example.shop_user.service.DiscountRuleService;
import com.example.shop_user.service.UserService;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.time.SessionPseudoClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountRuleServiceImpl implements DiscountRuleService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountRuleServiceImpl.class);

    @Resource
    private KieContainer kieContainer;
    @Resource
    private UserService userService;


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
            kieSession.setGlobal("userService",userService);
            kieSession.setGlobal("logger",  LoggerFactory.getLogger(this.getClass()));
            // 插入事实
            kieSession.insert(order);
            kieSession.insert(clock);
            // 执行规则
            kieSession.fireAllRules();

            // 获取规则结果
            QueryResults results = kieSession.getQueryResults("DiscountResults");
            if (results.size() <= 0) return null;
            List<RuleResult> resultList = new ArrayList<>();
            results.forEach(o->{
                RuleResult data = (RuleResult) o.get("$result");
                resultList.add(data);
            });
            return resultList.get(0);
        } finally {
            kieSession.dispose();
        }

    }


}

package com.example.shop_product.common;


import com.example.shop_common.common.context.SystemContext;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Component
public class GlobalInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);

    private static final Set<String> ignoreUrlList = Sets.newHashSet(
            //swagger相关放行
            "/swagger",
            "/webjars",
            "/configuration/ui",
            "/swagger-resources",
            "/v2/api-docs",
            "/configuration/security",
            "/sendDirectMessage",
            "/images",
            "/error"
    );


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        for (String s : ignoreUrlList) {
            if (requestURI.contains(s)){
                return true;
            }
        }
        logger.info("正在访问URI:{}", requestURI);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SystemContext.clear();
    }
}

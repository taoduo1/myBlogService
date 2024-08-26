package com.example.shop_user.common.intercepter;


import com.example.shop_common.common.context.SystemContext;
import com.example.shop_common.common.context.UserContext;
import com.example.shop_common.common.dto.CoreException;
import com.example.shop_common.utils.DataUtil;
import com.example.shop_user.entity.User;
import com.example.shop_user.service.IUserService;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class GlobalInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);

    private static final Set<String> ignoreUrlList = Sets.newHashSet(
            "/user/user/loginUser",
            "/user/user/createUser",
            //swagger相关放行
            "/swagger",
            "/webjars",
            "/configuration/ui",
            "/swagger-resources",
            "/v2/api-docs",
            "/configuration/security",
            "/sendDirectMessage"
    );
    @Resource
    private IUserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        logger.info("正在访问URI:{}", requestURI);
        for (String s : ignoreUrlList) {
            if (requestURI.contains(s)){
                return true;
            }
        }
        String token = request.getHeader("token");
        if (DataUtil.isNullOrEmpty(token)){
            throw new CoreException("请登陆！");
        }
        User user = userService.getUserByToken(token);
        if (DataUtil.isNull(user)){
            throw new CoreException("登录失效，请重新登陆！");
        }
        SystemContext.setContext(new UserContext(){{
            setToken(token);
            setTenantId(user.getTenantId());
            setUserId(user.getId());
            setUserName(user.getUsername());
        }});
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SystemContext.clear();
    }
}

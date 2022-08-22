package com.example.shop_user.common.intercepter;


import com.example.shop_common.common.context.SystemContext;
import com.example.shop_common.common.context.UserContext;
import com.example.shop_common.common.dto.CoreException;
import com.example.shop_common.utils.DataUtil;
import com.example.shop_user.entity.User;
import com.example.shop_user.service.IUserService;
import com.example.shop_user.util.MBeanUtils;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class GlobalInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);

    private static final Set<String> ignoreUrlList = Sets.newHashSet(
            "/api/user/user/login"
    );


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        logger.info("正在访问URI:{}", requestURI);
        if (ignoreUrlList.contains(requestURI)) {
            return true;
        }
        String token = request.getHeader("token");
        if (DataUtil.isNullOrEmpty(token)){
            throw new CoreException("请登陆！");
        }
        User user = MBeanUtils.getBean(IUserService.class).getUserByToken(token);
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
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SystemContext.clear();
    }
}

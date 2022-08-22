package com.example.shop_user.config;

import com.example.shop_user.common.intercepter.GlobalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class mvcConfig implements WebMvcConfigurer {

    @Bean //将组件注册在容器
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                InterceptorRegistration interceptor = registry.addInterceptor(new GlobalInterceptor());
                interceptor.addPathPatterns("/**");
                //interceptor.excludePathPatterns("/","/index.html","/user/login");
            }
        };
        return webMvcConfigurer;
    }
}

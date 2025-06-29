package com.example.shop_user;

import com.example.shop_user.config.loadbalancer.LoadBalancerConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //Feign 请求发送
@EnableDiscoveryClient //服务注册
@MapperScan("com.example.shop_user.mapper")
@LoadBalancerClient(name = "gateway-service", configuration = LoadBalancerConfig.class)
public class ShopUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopUserApplication.class, args);
    }
}

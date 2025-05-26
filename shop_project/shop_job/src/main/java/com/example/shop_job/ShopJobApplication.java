package com.example.shop_job;

import com.example.shop_job.config.loadbalancer.LoadBalancerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //Feign 请求发送
@EnableDiscoveryClient //服务注册
@LoadBalancerClient(name = "gateway-service", configuration = LoadBalancerConfig.class)
public class ShopJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopJobApplication.class, args);
    }

}

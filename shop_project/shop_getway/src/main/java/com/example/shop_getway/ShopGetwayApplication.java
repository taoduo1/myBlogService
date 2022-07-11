package com.example.shop_getway;

import com.example.shop_getway.components.loadbalancer.LoadBalancerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

@SpringBootApplication
@EnableDiscoveryClient
@LoadBalancerClients(
value = {
        @LoadBalancerClient(name = "user-service", configuration = LoadBalancerConfig.class),
        @LoadBalancerClient(name = "prod-service", configuration = LoadBalancerConfig.class)
}, defaultConfiguration = LoadBalancerConfig.class
)//先进入全局的，再进入自定义的
public class ShopGetwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopGetwayApplication.class, args);
    }

}

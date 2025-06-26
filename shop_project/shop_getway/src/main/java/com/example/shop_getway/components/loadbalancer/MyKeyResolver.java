package com.example.shop_getway.components.loadbalancer;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class MyKeyResolver implements KeyResolver {


    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        String remoteAddr = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
        System.out.println("远程访问的地址是"+remoteAddr);
        return Mono.just(remoteAddr);
    }
}

package com.example.shop_getway.components;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-07-07 21:18
 */

@Component
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {
    private static final Log logger = LogFactory.getLog(AbstractGatewayFilterFactory.class);

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
    }


    private List<String> ignoreUrI = Arrays.asList("/user/user/loginUser");


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            /*if (ignoreUrI.contains(request.getURI().getPath())){
                return chain.filter(exchange);
            }*/
//            String token = request.getHeaders().getFirst("token");
//            logger.info("token:" + token);
//            ServerHttpResponse response = exchange.getResponse();
//            if (StringUtils.isEmpty(token)) {
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return response.setComplete();
//            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}
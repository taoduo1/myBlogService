package com.example.shop_product.otherinterfince.hystrix.service;

import com.example.shop_product.otherinterfince.hystrix.service.impl.UserGetWayServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-07-11 22:05
 */
@Service
@FeignClient(name = "gateway-service", fallback = UserGetWayServiceHystrix.class)
public interface UserGetWayService {

    @RequestMapping(value = "/api/user/user/findById/{id}")
    String getStr(@PathVariable("id") Integer id);
}

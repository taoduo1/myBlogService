package com.example.shop_product.otherinterfince.hystrix.service;


import com.example.shop_product.otherinterfince.hystrix.service.impl.UserServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(name = "user-server", fallback = UserServiceHystrix.class)
public interface UserService {

    @RequestMapping(value = "/user/user/findById/{id}")
    String getStr(@PathVariable("id") Integer id);
}

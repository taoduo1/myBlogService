package com.example.shop_product.otherinterfince.hystrix.service.impl;

import com.example.shop_product.otherinterfince.hystrix.service.UserGetWayService;
import org.springframework.stereotype.Service;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-07-08 21:40
 */

@Service
public class UserGetWayServiceHystrix implements UserGetWayService {

    @Override
    public String getStr(Integer id) {
        return "hello, this message send failed ";
    }
}

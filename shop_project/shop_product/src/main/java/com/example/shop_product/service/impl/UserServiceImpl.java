package com.example.shop_product.service.impl;

import com.example.shop_product.otherinterfince.hystrix.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-07-07 22:07
 */
@Service
public class UserServiceImpl implements com.example.shop_product.service.UserService {

    @Resource
    private UserService userService;


    @Override
    public String getUserById(Integer id) {
        String str = userService.getStr(id);
        return str;
    }
}

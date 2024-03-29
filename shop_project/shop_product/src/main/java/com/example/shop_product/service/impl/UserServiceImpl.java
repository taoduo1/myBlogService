package com.example.shop_product.service.impl;

import com.example.shop_product.otherinterfince.user.service.UserGetWayService;
import com.example.shop_product.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-07-07 22:07
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserGetWayService userService;

    @Override
    public String getUserById(Integer id) {
        String str = userService.getStr(id);
        return str;
    }
}

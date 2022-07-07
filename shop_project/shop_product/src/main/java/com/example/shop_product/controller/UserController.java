package com.example.shop_product.controller;

import com.example.shop_product.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-07-07 22:08
 */
@RestController
@RequestMapping("/product/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/getStr2")
    public String getStr2(){
        String str = userService.getUserById(1);
        return str;
    }
}

package com.example.shop_user;

import com.example.shop_user.entity.User;
import com.example.shop_user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ShopUserApplicationTests {

    @Resource
    private UserService userService;
    @Test
    void contextLoads() {
        User data = userService.getUserByToken("123");
        System.out.println(1);
    }

}

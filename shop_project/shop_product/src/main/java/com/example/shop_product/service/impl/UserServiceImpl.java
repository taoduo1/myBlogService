package com.example.shop_product.service.impl;

import com.example.shop_common.common.response.ActionResult;
import com.example.shop_product.otherinterfince.user.service.UserGetWayService;
import com.example.shop_product.service.UserService;
import com.example.shop_product.thirdto.User;
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
    public ActionResult<User> getUserById(Integer id) {
        ActionResult<User> user = userService.getStr(id);
        return user;
    }
}

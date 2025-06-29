package com.example.shop_product.controller;

import com.example.shop_common.common.response.ActionResult;
import com.example.shop_product.service.UserService;
import com.example.shop_product.thirdto.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @ApiOperation(value = "根据id获取某个用户", notes = "根据id获取某个用户")
    @GetMapping("/findById/{id}")
    public ActionResult<User> findById(@PathVariable Integer id){
        ActionResult<User> str = userService.getUserById(id);
        return str;
    }
}

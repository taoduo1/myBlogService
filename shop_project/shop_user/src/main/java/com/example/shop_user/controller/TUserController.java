package com.example.shop_user.controller;


import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.utils.ResultUtil;
import com.example.shop_user.dto.LoginUser;
import com.example.shop_user.dto.RegisterUserDto;
import com.example.shop_user.entity.TUser;
import com.example.shop_user.service.ITUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author duo.tao
 * @since 2022-02-25
 */
@RestController
@RequestMapping("/user")
@Api("用户controller")
public class TUserController {

    @Autowired
    private ITUserService userService;


    @GetMapping("/getUser")
    public ActionResult<TUser> getUser() {
        return ResultUtil.ok(userService.getById(1));
    }

    @PostMapping("/findAllUser")
    public ActionResult<List<TUser>> findAllUser() {
        return ResultUtil.ok(userService.findAllUser());
    }

    @PostMapping("/addUser")
    @ApiOperation(value = "用戶新增")
    public ActionResult<TUser> addUser(@ApiParam @RequestBody TUser tUser) {
        return ResultUtil.ok(userService.addUser(tUser));
    }

    @PostMapping("/login")
    @ApiOperation(value = "用戶登录")
    public ActionResult<String> login(@ApiParam @RequestBody LoginUser user) {
        return ResultUtil.ok(userService.login(user.getUsername(), user.getPassword()));
    }

    @PostMapping("/register")
    @ApiOperation(value = "用戶注册")
    public ActionResult<String> register(@Validated @ApiParam @RequestBody RegisterUserDto user) {
        userService.register(user);
        return ResultUtil.ok();
    }


}

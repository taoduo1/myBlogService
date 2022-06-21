package com.example.shop_user.controller;


import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.utils.ResultUtil;
import com.example.shop_user.dto.LoginUserDto;
import com.example.shop_user.dto.RegisterUserDto;
import com.example.shop_user.entity.User;
import com.example.shop_user.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author duo.tao
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation(value = "新建用户", notes = "新建用户")
    @PostMapping(value = "/createUser")
    public ActionResult<User> createUser(@Valid @RequestBody RegisterUserDto registerUserDto, HttpServletRequest  httpRequest) throws NoSuchAlgorithmException {
        return ResultUtil.ok(userService.createNewUser(registerUserDto,httpRequest));
    }


    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping(value = "/loginUser")
    public ActionResult<String> loginUser(@Valid @RequestBody LoginUserDto loginUserDto, HttpServletRequest httpRequest) throws NoSuchAlgorithmException {
        return ResultUtil.ok(userService.loginUser(loginUserDto,httpRequest));
    }

    @ApiOperation(value = "根据id获取某个用户", notes = "根据id获取某个用户")
    @GetMapping(value = "/findById/{id}")
    public ActionResult<User> findById(@PathVariable Integer id) {
        return ResultUtil.ok(userService.get(id));
    }




}

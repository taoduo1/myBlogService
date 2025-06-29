package com.example.shop_user.controller;


import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.utils.ResultUtil;
import com.example.shop_user.dto.AuthResponse;
import com.example.shop_user.dto.LoginUserDto;
import com.example.shop_user.dto.RegisterUserDto;
import com.example.shop_user.entity.User;
import com.example.shop_user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "新建用户-管理租户注册用户", notes = "新建用户-管理租户注册用户")
    @PostMapping(value = "/createUser")
    public ActionResult<User> createUser(@Valid @RequestBody RegisterUserDto registerUserDto, HttpServletRequest  httpRequest) throws NoSuchAlgorithmException {
        return ResultUtil.ok(userService.createNewUser(registerUserDto,httpRequest));
    }


    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping(value = "/loginUser")
    public ActionResult<AuthResponse> loginUser(@Valid @RequestBody LoginUserDto loginUserDto, HttpServletRequest httpRequest) throws NoSuchAlgorithmException {
        return ResultUtil.ok(userService.loginUser(loginUserDto,httpRequest));
    }

    @ApiOperation(value = "根据id获取某个用户", notes = "根据id获取某个用户")
    @GetMapping(value = "/findById/{id}")
    public ActionResult<User> findById(@PathVariable Integer id) {
        log.info("获取用户{}",id);
        User data = userService.get(id);
        return ResultUtil.ok(data);
    }

    @ApiOperation(value = "用户注销", notes = "用户注销")
    @GetMapping(value = "/loginOut")
    public ActionResult<Void> loginOut(HttpServletRequest httpRequest) {
        userService.loginOut(httpRequest.getHeader("token"));
        return ResultUtil.ok();
    }




}

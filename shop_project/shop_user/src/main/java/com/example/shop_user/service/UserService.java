package com.example.shop_user.service;

import com.example.shop_common.common.service.CrudService;
import com.example.shop_user.dto.LoginUserDto;
import com.example.shop_user.dto.RegisterUserDto;
import com.example.shop_user.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author duo.tao
 * @since 2022-06-13
 */
public interface UserService extends CrudService<User> {

    User createNewUser(RegisterUserDto user, HttpServletRequest httpRequest) throws NoSuchAlgorithmException;

    String loginUser(LoginUserDto user, HttpServletRequest httpRequest) throws NoSuchAlgorithmException;

    void loginOut(String token);

    /**
     *  token redis 操作接口
     * @param token
     * @param user
     */
    void setUserToken(String token, User user);
    User getUserByToken(String token);
    void cleanUserTokenCache(String token);

}

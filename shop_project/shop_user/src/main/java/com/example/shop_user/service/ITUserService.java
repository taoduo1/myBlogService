package com.example.shop_user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shop_user.dto.RegisterUserDto;
import com.example.shop_user.entity.TUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author duo.tao
 * @since 2022-02-25
 */
public interface ITUserService extends IService<TUser> {

    List<TUser> findAllUser();

    TUser addUser(TUser tUser);



    String login(String userName,String password);


    void setToken(String md5Str, String token);

    String getToken(String md5Str);

    void cleanToken(String md5Str);

    void register(RegisterUserDto tUser);


}

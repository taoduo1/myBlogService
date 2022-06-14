package com.example.shop_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shop_common.common.dto.CoreException;
import com.example.shop_common.common.enums.user.UserErrorEnum;
import com.example.shop_common.common.enums.user.UserLevelEnum;
import com.example.shop_common.common.service.CrudServiceImpl;
import com.example.shop_common.utils.HttpUtils;
import com.example.shop_common.utils.MD5Utils;
import com.example.shop_user.dto.RegisterUserDto;
import com.example.shop_user.entity.User;
import com.example.shop_user.mapper.UserMapper;
import com.example.shop_user.service.IUserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author duo.tao
 * @since 2022-06-13
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User createNewUser(RegisterUserDto user, HttpServletRequest httpRequest) throws NoSuchAlgorithmException {
        //前置校验
        if (!user.getPassword().equals(user.getConfirmPassword())){
            throw new CoreException(UserErrorEnum.USER_ENTERED_TWO_DIFFERENT_PASSWORDS.getName());
        }
        //后置校验
        QueryWrapper<User> wrapper = new QueryWrapper();
        int count = selectCount(wrapper.eq("user_name", user.getUsername()));
        if (count > 0) {
            throw new CoreException(UserErrorEnum.USER_ALREADY_EXISTS.getName());
        }
        //新建用户
        User newUser = new User() {{
            setUsername(user.getUsername());
            String ipAddress = HttpUtils.getIP(httpRequest);
            setRegisterIp(ipAddress);
            setLevel(UserLevelEnum.LEVEL_ONE.getIndex());
            setPassword(MD5Utils.getMD5Str(user.getPassword() + ipAddress));//注册ip为盐进行md5加密，达到每个用户拥有不同的盐的效果
        }};
        //dao.save(newUser);
        return newUser;
    }

    @Override
    public User getById(Integer id) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("id",id);
        User data = dao.selectById(id);
        return data;
    }
}

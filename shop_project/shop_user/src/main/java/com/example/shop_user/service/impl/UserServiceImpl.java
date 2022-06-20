package com.example.shop_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shop_common.common.dto.CoreException;
import com.example.shop_common.common.enums.user.UserErrorEnum;
import com.example.shop_common.common.enums.user.UserLevelEnum;
import com.example.shop_common.common.service.CrudServiceImpl;
import com.example.shop_common.utils.DataUtil;
import com.example.shop_common.utils.HttpUtils;
import com.example.shop_common.utils.MD5Utils;
import com.example.shop_user.dto.LoginUserDto;
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
        //校验密码
        if (!user.getPassword().equals(user.getConfirmPassword())){
            throw new CoreException(UserErrorEnum.USER_ENTERED_TWO_DIFFERENT_PASSWORDS.getName());
        }
        //校验用户是否存在
        int count = selectCount(new QueryWrapper<User>().eq("user_name", user.getUsername()));
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
        save(newUser);
        return newUser;
    }

    @Override
    public String loginUser(LoginUserDto loginUser) throws NoSuchAlgorithmException {
        User user = findOneByWrapper(new QueryWrapper<User>().eq("username", loginUser.getUsername()));
        if (DataUtil.isNull(user)){
            throw new CoreException(UserErrorEnum.USER_USERNAME_OR_PASSWORD_ERROR.getName());
        }
        if (!user.getPassword().equals(MD5Utils.getMD5Str(loginUser.getPassword() + user.getRegisterIp()))){
            throw new CoreException(UserErrorEnum.USER_USERNAME_OR_PASSWORD_ERROR.getName());
        }
        //用户登录成功，保存用户信息至redis
        return null;
    }

}

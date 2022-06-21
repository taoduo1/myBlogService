package com.example.shop_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shop_common.common.constant.NormalConstant;
import com.example.shop_common.common.dto.CoreException;
import com.example.shop_common.common.enums.user.UserErrorEnum;
import com.example.shop_common.common.enums.user.UserLevelEnum;
import com.example.shop_common.common.redis.annotation.RedisCacheDelete;
import com.example.shop_common.common.redis.annotation.RedisCacheGet;
import com.example.shop_common.common.redis.annotation.RedisCachePut;
import com.example.shop_common.common.service.CrudServiceImpl;
import com.example.shop_common.utils.DataUtil;
import com.example.shop_common.utils.HttpUtils;
import com.example.shop_common.utils.MBeanUtils;
import com.example.shop_common.utils.MD5Utils;
import com.example.shop_user.common.exception.UserConstant;
import com.example.shop_user.dto.LoginUserDto;
import com.example.shop_user.dto.RegisterUserDto;
import com.example.shop_user.entity.Tenant;
import com.example.shop_user.entity.User;
import com.example.shop_user.mapper.UserMapper;
import com.example.shop_user.service.ITenantService;
import com.example.shop_user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private ITenantService tenantService;

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
        Tenant tenant = tenantService.findOneByWrapper(new QueryWrapper<Tenant>().eq("name", UserConstant.DEFAULT_USER_TENANT_NAME));
        //新建用户
        User newUser = new User() {{
            setUsername(user.getUsername());
            String ipAddress = HttpUtils.getIP(httpRequest);
            setRegisterIp(ipAddress);
            setLevel(UserLevelEnum.LEVEL_ONE.getIndex());
            setPassword(MD5Utils.getMD5Str(user.getPassword() + ipAddress));//注册ip为盐进行md5加密，达到每个用户拥有不同的盐的效果
            setTenantId(tenant.getId());
        }};
        save(newUser);
        return newUser;
    }

    @Override
    public String loginUser(LoginUserDto loginUser, HttpServletRequest httpRequest) throws NoSuchAlgorithmException {
        User user = findOneByWrapper(new QueryWrapper<User>().eq("username", loginUser.getUsername()));
        if (DataUtil.isNull(user)){
            throw new CoreException(UserErrorEnum.USER_USERNAME_OR_PASSWORD_ERROR.getName());
        }
        if (!user.getPassword().equals(MD5Utils.getMD5Str(loginUser.getPassword() + user.getRegisterIp()))){
            throw new CoreException(UserErrorEnum.USER_USERNAME_OR_PASSWORD_ERROR.getName());
        }
        //用户登录成功，更新用户登录信息
        String ipAddress = HttpUtils.getIP(httpRequest);
        user.setLastLoginIp(ipAddress);
        user.setLastLoginTime(new Date());
        save(user);
        //token写入redis
        String token = MD5Utils.getMD5Str(user.getUsername());
        IUserService bean = MBeanUtils.getBean(IUserService.class);
        bean.setUserToken(token,user);
        return token;
    }

    @RedisCachePut(key = NormalConstant.REDIS_TOKEN_KEY, minutes = 2 * 60)
    @Override
    public void setUserToken(String token, User user) {
        // 由AOP实现
    }

    @RedisCacheGet(key = NormalConstant.REDIS_TOKEN_KEY, minutes = 2 * 60)
    @Override
    public User getUserToken(String token) {
        logger.info("redis-session无效");
        throw new CoreException("请登陆！");
    }

    @RedisCacheDelete(key = NormalConstant.REDIS_TOKEN_KEY)
    @Override
    public void cleanUserTokenCache(String token) {
        // 由AOP实现
    }


}

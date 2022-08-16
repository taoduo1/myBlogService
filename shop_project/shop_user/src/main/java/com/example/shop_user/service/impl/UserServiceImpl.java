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
import com.example.shop_common.utils.MD5Utils;
import com.example.shop_user.common.constant.UserConstant;
import com.example.shop_user.dto.LoginUserDto;
import com.example.shop_user.dto.RegisterUserDto;
import com.example.shop_user.entity.Tenant;
import com.example.shop_user.entity.User;
import com.example.shop_user.mapper.UserMapper;
import com.example.shop_user.service.ITenantService;
import com.example.shop_user.service.IUserService;
import com.example.shop_user.util.MBeanUtils;
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
        //判断用户注册类型：如果是自注册用户直接放行，如果是管理租户或者是集团租户用户注册，则校验集团不能为空
        if (DataUtil.notNull(user.getTenantType()) && DataUtil.isNullOrEmpty(user.getGroupCode())) {
            throw new CoreException(UserErrorEnum.USER_TENANT_CODE_IS_NULL.getName());
        }
        //校验密码
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new CoreException(UserErrorEnum.USER_ENTERED_TWO_DIFFERENT_PASSWORDS.getName());
        }
        //校验用户是否存在
        int count = selectCount(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (count > 0) {
            throw new CoreException(UserErrorEnum.USER_ALREADY_EXISTS.getName());
        }
        Tenant tenant = tenantService.findOneByWrapper(new QueryWrapper<Tenant>().eq("code", DataUtil.notNullOrEmpty(user.getGroupCode()) ? user.getGroupCode() : UserConstant.TENANT_REGISTER_CODE));
        if (DataUtil.isNull(tenant)) {
            throw new CoreException(UserErrorEnum.USER_TENANT_IS_NULL.getName());
        }
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
        if (DataUtil.isNull(user)) {
            throw new CoreException(UserErrorEnum.USER_USERNAME_OR_PASSWORD_ERROR.getName());
        }
        if (!user.getPassword().equals(MD5Utils.getMD5Str(loginUser.getPassword() + user.getRegisterIp()))) {
            throw new CoreException(UserErrorEnum.USER_USERNAME_OR_PASSWORD_ERROR.getName());
        }
        //用户登录成功，更新用户登录信息
        String ipAddress = HttpUtils.getIP(httpRequest);
        user.setLastLoginIp(ipAddress);
        user.setLastLoginTime(new Date());
        save(user);
        String token = MD5Utils.getMD5Str(user.getUsername());
        //用户信息写入redis
        MBeanUtils.getBean(IUserService.class).setUserToken(token, user);
        // TODO: 2022/8/2 用户权限写入
        return token;
    }

    @Override
    public void loginOut(String token) {
        User user = MBeanUtils.getBean(IUserService.class).getUserByToken(token);
        if (DataUtil.isNull(user)) {
            throw new CoreException(UserErrorEnum.USER_TOKEN_TIME_OUT.getName());
        }
        MBeanUtils.getBean(IUserService.class).cleanUserTokenCache(token);
    }


    @Deprecated
    @RedisCachePut(key = NormalConstant.REDIS_TOKEN_KEY, minutes = 2 * 60)
    @Override
    public void setUserToken(String token, User user) {
        // 由AOP实现
    }

    @Deprecated
    @RedisCacheGet(key = NormalConstant.REDIS_TOKEN_KEY, minutes = 2 * 60)
    @Override
    public User getUserByToken(String token) {
        logger.info("redis-session无效");
        throw new CoreException("请登陆！");
    }

    @Deprecated
    @RedisCacheDelete(key = NormalConstant.REDIS_TOKEN_KEY)
    @Override
    public void cleanUserTokenCache(String token) {
        // 由AOP实现
    }


}

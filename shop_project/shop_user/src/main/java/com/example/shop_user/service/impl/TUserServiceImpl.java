package com.example.shop_user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shop_common.common.dto.CoreException;
import com.example.shop_common.common.redis.annotation.RedisCacheDelete;
import com.example.shop_common.common.redis.annotation.RedisCacheGet;
import com.example.shop_common.common.redis.annotation.RedisCachePut;
import com.example.shop_common.utils.DataUtil;
import com.example.shop_user.dto.RegisterUserDto;
import com.example.shop_user.entity.TUser;
import com.example.shop_user.mapper.TUserMapper;
import com.example.shop_user.service.ITUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author duo.tao
 * @since 2022-02-25
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

    private static final Logger logger = LoggerFactory.getLogger(TUserServiceImpl.class);

    private final static String REDIS_TOKEN_KEY = "token";

    @Resource
    private TUserMapper mapper;

    @Override
    public List<TUser> findAllUser() {
        return mapper.findAllUser();
    }

    @Override
    public TUser addUser(TUser tUser) {
        mapper.insert(tUser);
        return tUser;
    }

    @Override
    public String login(String userName, String password) {
        TUser tUser = mapper.selectOne(new QueryWrapper<TUser>().eq("username", userName));
        //生成jwt令牌，并返回前端
        String userJson = JSON.toJSONString(tUser);//序列化user
        JwtBuilder jwtBuilder = Jwts.builder(); //获得JWT构造器
        Map<String, Object> map = new Hashtable<>();
        map.put("kay", userJson);
        String token = jwtBuilder.setSubject("hello") //设置用户数据
                .setIssuedAt(new Date()) //设置jwt生成时间
                .setId("1") //设置id为token id
                .setClaims(map) //通过map传值
                .setExpiration(new Date(System.currentTimeMillis() + 500000)) //设置token有效期
                .signWith(SignatureAlgorithm.HS256, "java666") //设置token加密方式和密码
                .compact(); //生成token字符串

        //解析jwt令牌
        JwtParser jwtParser = Jwts.parser(); //获取jwt解析器
        jwtParser.setSigningKey("java666");
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        Claims body = claimsJws.getBody();//获取body
        String subject = body.getSubject();//获取body中subject中的值
        String key = body.get("key", String.class);//获取Claims中map的值
        TUser user = JSON.parseObject(key, TUser.class);//反序列化user
        return token;
    }

    @Override
    public void register(RegisterUserDto tUser) {
        //1：查询用户是否已经存在
        //2：用户存在抛出异常，不存在则进行用户密码
        if (DataUtil.isNull(tUser)){
            throw new CoreException("用户为空");
        }
        TUser dbUser = mapper.selectOne(new QueryWrapper<TUser>().eq("username", tUser.getUsername()));
        if (DataUtil.notNull(dbUser)){
            throw new CoreException("用户名已存在");
        }


    }


    @RedisCachePut(key = REDIS_TOKEN_KEY, minutes = 24 * 60)
    @Override
    public void setToken(String md5Str, String token) {
        // 由AOP实现
    }

    @RedisCacheGet(key = REDIS_TOKEN_KEY, minutes = 24 * 60)
    @Override
    public String getToken(String md5Str) {

        return null;
    }

    @RedisCacheDelete(key = REDIS_TOKEN_KEY)
    @Override
    public void cleanToken(String md5Str) {
        // 由AOP实现
    }


}

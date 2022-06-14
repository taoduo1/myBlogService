package com.example.shop_common.common.redis;

import com.example.shop_common.common.redis.annotation.RedisCacheDelete;
import com.example.shop_common.common.redis.annotation.RedisCacheGet;
import com.example.shop_common.common.redis.annotation.RedisCachePut;
import com.example.shop_common.utils.CollectionUtils;
import com.example.shop_common.utils.JSONUtils;
import com.google.common.collect.Maps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author duo.tao
 * @Description: 缓存使用方法
 * @date 2022-06-13 23:14
 */
@Component
@Aspect
@Lazy
public class CacheAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheAspect.class);

    @Autowired
    @Qualifier("stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    /**
     * Handle cache get.
     *
     * @param pjp  the pjp
     * @param anno the anno
     * @return the object
     * @throws Throwable the throwable
     */
    // 在使用Cache注解的地方切入此切点
    @Around("@annotation(anno)")
    public Object handleCacheGet(ProceedingJoinPoint pjp, RedisCacheGet anno) throws Throwable {
        LOGGER.debug("缓存获取 {}", anno.key());
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        // 解析key(dataAuthMap:最后一位参数是二级key(map中的key)，除了最后一位都参与构建key， 其他)
        String key = genKey(anno.key(),anno.enableMap() ?
                Arrays.copyOf(pjp.getArgs(), pjp.getArgs().length - 1) : pjp.getArgs());
        Object result;
        String json;
        if (anno.enableMap()) {
            HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
            json = (String) hashOperations.get(key, pjp.getArgs()[pjp.getArgs().length - 1]);
        } else {
            json = redisTemplate.opsForValue().get(key);
        }

        if (json != null) {
            LOGGER.debug("缓存获得 key={}", key);
            Type returnType = method.getGenericReturnType();
            result = JSONUtils.fromJSONString(json, returnType);
            return result;
        }
        LOGGER.info("缓存未获得 key={}", key);
        // 若不存在，则运行函数(从数据库中去获取)生成
        result = pjp.proceed();
        if (anno.enableMap()) {
            String mapKey = String.valueOf(pjp.getArgs()[pjp.getArgs().length - 1]);
            if (result != null) {
                Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(1);
                resultMap.put(mapKey, result);
                updateCache(key, resultMap, anno.minutes());
            } else {
                updateCache(key, null, anno.minutes());
            }
        } else {
            updateCache(key, result, anno.minutes());
        }
        return result;
    }

    /**
     * Handle cache put.
     *
     * @param pjp  the pjp
     * @param anno the anno
     */
    // 在使用Cache注解的地方切入此切点
    @Before("@annotation(anno)")
    public void handleCachePut(JoinPoint pjp, RedisCachePut anno) {
        LOGGER.debug("缓存更新 {}", anno.key());
        // 解析key
        Object[] args = pjp.getArgs();
        String key = genKey(anno.key(), Arrays.copyOf(args, args.length - 1));
        Object result = args[args.length - 1];
        if (anno.enableMap()) {
            updateCache(key, (Map<String, Object>) result, anno.minutes());
        } else {
            updateCache(key, result, anno.minutes());
        }
    }

    private void updateCache(String key, Object result, long minutes) {
        LOGGER.debug("缓存更新 key={}", key);
        if (result != null) {
            if (minutes < 0) {
                redisTemplate.opsForValue().set(key, JSONUtils.toJSONString(result));
            } else {
                redisTemplate.opsForValue().set(key, JSONUtils.toJSONString(result), minutes, TimeUnit.MINUTES);
            }

        } else {
            redisTemplate.delete(key);
        }

    }

    private void updateCache(String key, Map<String, Object> result, long minutes) {
        LOGGER.debug("缓存更新 key={}", key);
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        if (result != null) {
            Map<String, String> map = new HashMap<>();
            result.forEach((k, v) -> map.put(k, JSONUtils.toJSONString(v)));
            hashOperations.putAll(key, map);
            hashOperations.getOperations().expire(key, minutes, TimeUnit.MINUTES);
        } else {
            hashOperations.delete(key);

        }

    }

    /**
     * Handle cache delete.
     *
     * @param pjp  the pjp
     * @param anno the anno
     */
    // 在使用Cache注解的地方切入此切点
    @After("@annotation(anno)")
    public void handleCacheDelete(JoinPoint pjp, RedisCacheDelete anno) {
        LOGGER.debug("缓存删除 {}", anno.key());
        // 解析key
        String key = genKey(anno.key(), pjp.getArgs());
        LOGGER.info("缓存删除 key={}", key);
        Set<String> keys = CollectionUtils.mergeSet(redisTemplate.keys(key), redisTemplate.keys(key + ":*"));
        LOGGER.debug("缓存删除 count={}", keys.size());
        keys.forEach(s -> redisTemplate.delete(s));

    }

    /**
     * @param key
     * @param args
     * @return
     * @Description 根据参数，拼接成key字符串。
     */
    private String genKey(String key,  Object[] args) {
        StringBuilder sb = new StringBuilder(key);
        Arrays.stream(args).forEach(s -> sb.append(":").append(s));
        return sb.toString();
    }

}

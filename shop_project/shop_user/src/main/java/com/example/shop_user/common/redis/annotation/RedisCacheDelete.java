package com.example.shop_user.common.redis.annotation;

import java.lang.annotation.*;

/**
 * @author duo.tao
 * @Description: 缓存注解。.
 * @date 2022-06-13 23:14
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisCacheDelete {

    /**
     * Key.
     *
     * @return the string
     */
    String key();


}

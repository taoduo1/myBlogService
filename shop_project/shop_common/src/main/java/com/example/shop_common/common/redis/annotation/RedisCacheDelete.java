package com.example.shop_common.common.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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

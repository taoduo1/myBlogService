package com.example.shop_user.common.redis.annotation;

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
public @interface RedisCachePut {

    /**
     * Key.
     *
     * @return the string
     */
    String key();

    /**
     * Minutes.
     *
     * @return the int
     */
    int minutes() default 8 * 60;

    /**
     * enable HashOperations
     * @return
     */
    boolean enableMap() default false;

    /**
     * 是否自动将tenantId作为key的一部分。
     * <pre>
     * 默认false，表示不会将tenantId放入key。
     * true时，则会将tenantId作为key的一部分。
     * </pre>.
     *
     * @return true, if successful
     */
    boolean autoIncludeTenantId() default false;


    String type() default "hash";
}

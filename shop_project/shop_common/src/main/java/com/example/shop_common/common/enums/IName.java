package com.example.shop_common.common.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 枚举 接口
 *
 * @author TIM(JT)
 * @date 2017-05-22 18
 **/
public interface IName extends IToMap {

    String getName();

    /**
    * 当前枚举项的值（name）是否匹配传入参数。
    * @param name
    * @return
    */
    default boolean matchName(String name) {
        return Optional.ofNullable(name).map(v -> getName().equals(v)).orElse(false);
    }

    @Override
    default Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        return map;
    }

}

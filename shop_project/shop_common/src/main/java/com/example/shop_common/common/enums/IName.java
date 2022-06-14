package com.example.shop_common.common.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author duo.tao
 * @Description: 枚举 接口
 * @date 2022-06-13 23:14
 */
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

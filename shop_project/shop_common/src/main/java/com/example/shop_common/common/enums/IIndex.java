package com.example.shop_common.common.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author duo.tao
 * @Description: 枚举 接口
 * @date 2022-06-13 23:14
 */
public interface IIndex extends IToMap {

    int getIndex();

    /**
     * 当前枚举项的值（value）是否匹配传入参数。
     * @return true or false
     */
    default boolean match(Integer value) {
        if (Objects.isNull(value)) return false;
        return value == getIndex();
    }

    /**
     * 当前枚举项的值（value）是否匹配传入参数。
     * @param value
     * @return
     */
    default boolean notMatch(Integer value) {
        return !match(value);
    }

    @Override
    default Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("index", getIndex());
        map.put("value", getIndex()); // 统一key为：value，取bool值
        return map;
    }
}

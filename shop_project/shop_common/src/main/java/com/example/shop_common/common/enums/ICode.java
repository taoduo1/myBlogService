package com.example.shop_common.common.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author duo.tao
 * @Description: 枚举 接口
 * @date 2022-06-13 23:14
 */
public interface ICode extends IToMap {

    String getCode();

    /**
     * 当前枚举项的值（code）是否匹配传入参数。
     * @param value
     * @return
     */
    default boolean match(String value) {
        return Optional.ofNullable(value).map(v -> getCode().equals(v)).orElse(false);
    }

    /**
     * 当前枚举项的值（code）是否匹配传入参数。
     * @param value
     * @return
     */
    default boolean notMatch(String value) {
        return !match(value);
    }

    @Override
    default Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", getCode());
        map.put("value", getCode()); // 统一key为：value，取bool值
        return map;
    }
}

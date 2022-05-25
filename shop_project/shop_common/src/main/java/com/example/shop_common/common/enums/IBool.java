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
public interface IBool extends IToMap {

	boolean getBool();

	/**
	 * 当前枚举项的值（code）是否匹配传入参数。
	 * @param value
	 * @return
	 */
	default boolean match(Boolean value) {
		return Optional.ofNullable(value).map(v -> v.equals(getBool())).orElse(false);
	}

	/**
	 * 当前枚举项的值（code）是否匹配传入参数。
	 * @param value
	 * @return
	 */
	default boolean notMatch(Boolean value) {
		return !match(value);
	}

    @Override
    default Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("bool", getBool());
        map.put("value", getBool()); // 统一key为：value，取bool值
        return map;

    }
}

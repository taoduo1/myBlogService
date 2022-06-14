package com.example.shop_common.common.enums.user;

import com.example.shop_common.common.enums.IIndex;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-06-13 23:27
 */
public enum UserLevelEnum implements IIndex {

    LEVEL_ONE(1,"自行注册用户"),
    LEVEL_TWO(2,"特殊用户"),
    ;

    private final int index;
    private final String value;

    UserLevelEnum(int index,String value) {
        this.index = index;
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    @Override
    public int getIndex() {
        return index;
    }
}

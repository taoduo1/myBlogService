package com.example.shop_common.common.enums.user;

import com.example.shop_common.common.enums.IIndex;

/**
 * @author duo.tao
 * @Description: 用户模块错误信息
 * @date 2022-06-13 23:14
 */
public enum TenantTypeEnum implements IIndex {

    USER_RULE_TENANT(1,"管理租户"),
    USER_REGISTER_TENANT(2,"自注册租户"),
    USER_GROUP_TENANT(3,"集团租户"),
    ;
    private final int index;
    private final String value;

    TenantTypeEnum(int index,String value) {
        this.index = index;
        this.value = value;
    }


    @Override
    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }
}

package com.example.shop_common.common.enums.user;

import com.example.shop_common.common.enums.IName;

/**
 * @author duo.tao
 * @Description: 用户模块错误信息
 * @date 2022-06-13 23:14
 */
public enum UserErrorEnum implements IName {

    USER_ALREADY_EXISTS("用户已存在"),
    USER_ENTERED_TWO_DIFFERENT_PASSWORDS("用户两次输入密码不一致"),
    ;

    private final String name;

    UserErrorEnum(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }
}

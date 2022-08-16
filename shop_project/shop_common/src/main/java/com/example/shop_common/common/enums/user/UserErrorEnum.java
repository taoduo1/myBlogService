package com.example.shop_common.common.enums.user;

import com.example.shop_common.common.enums.IName;

/**
 * @author duo.tao
 * @Description: 用户模块错误信息
 * @date 2022-06-13 23:14
 */
public enum UserErrorEnum implements IName {

    USER_ALREADY_EXISTS("用户已存在"),
    USER_TENANT_IS_NULL("集团为空"),
    USER_TENANT_CODE_IS_NULL("用户集团编号为空"),
    USER_ENTERED_TWO_DIFFERENT_PASSWORDS("用户两次输入密码不一致"),
    USER_USERNAME_OR_PASSWORD_ERROR("用户名或密码错误"),
    USER_TOKEN_TIME_OUT("用户登录过期"),

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

package com.example.shop_user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@ApiModel(value="用户注册对象")
public class RegisterUserDto {

    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "用户确认密码")
    private String confirmPassword;

    @ApiModelProperty(value = "手机号")
    @NotNull(message = "手机号不能为空")
    private String phone;


}

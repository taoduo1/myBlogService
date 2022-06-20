package com.example.shop_user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
@ApiModel(value="用户注册对象")
public class LoginUserDto {

    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不能为空")
    @Size(min = 6,max = 50)
    private String username;

    @ApiModelProperty(value = "密码")
    @Size(min = 6,max = 50)
    private String password;


}

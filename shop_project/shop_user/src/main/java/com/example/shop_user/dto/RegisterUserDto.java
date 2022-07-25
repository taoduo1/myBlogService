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
public class RegisterUserDto {

    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不能为空")
    @Size(min = 6,max = 50)
    private String username;

    @ApiModelProperty(value = "密码")
    @Size(min = 6,max = 50)
    private String password;

    @ApiModelProperty(value = "用户确认密码")
    @Size(min = 6,max = 50)
    private String confirmPassword;

    @ApiModelProperty(value = "手机号")
    @Size(min = 11,max = 11)
    private String phone;

    @ApiModelProperty(value = "集团标识码")
    @Size(min = 11,max = 11)
    private String groupCode;

    //租户类型
    private Integer tenantType;
}

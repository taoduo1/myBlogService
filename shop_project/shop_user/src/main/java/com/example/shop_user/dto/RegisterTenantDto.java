package com.example.shop_user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
@ApiModel(value="租户注册对象")
public class RegisterTenantDto {

    @ApiModelProperty(value = "租户名")
    @NotNull(message = "用户名不能为空")
    @Size(min = 4,max = 50)
    private String name;

    @ApiModelProperty(value = "编号")
    @Size(min = 4,max = 50)
    private String code;

    //租户类型
    @ApiModelProperty(value = "租户类型:TenantTypeEnum")
    private Integer tenantType;
}

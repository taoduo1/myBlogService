package com.example.shop_user.entity;

import com.example.shop_common.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 租户表
 * </p>
 *
 * @author duo.tao
 * @since 2022-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Tenant对象", description="租户表")
public class Tenant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "租户类型")
    private Integer type;
}

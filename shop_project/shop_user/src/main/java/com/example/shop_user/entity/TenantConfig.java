package com.example.shop_user.entity;

import com.example.shop_common.entity.base.BaseTenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 租户配置信息表
 * </p>
 *
 * @author duo.tao
 * @since 2022-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="TenantConfig对象", description="租户配置信息表")
public class TenantConfig extends BaseTenantEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "对应属性名")
    private Integer type;

    @ApiModelProperty(value = "属性值1")
    private String value1;

    @ApiModelProperty(value = "属性值2")
    private String value2;

    @ApiModelProperty(value = "属性值3")
    private String value3;

    @ApiModelProperty(value = "属性值4")
    private String value4;

    @ApiModelProperty(value = "属性值5")
    private String value5;

    @ApiModelProperty(value = "属性值")
    private String value6;

    @ApiModelProperty(value = "属性值")
    private String value7;

    @ApiModelProperty(value = "属性值")
    private String value8;

    @ApiModelProperty(value = "属性值")
    private String value9;

    @ApiModelProperty(value = "属性值")
    private String value10;

    @ApiModelProperty(value = "是否启用")
    private Integer enable;


}

package com.example.shop_product.entity;

import com.example.shop_common.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 奖品表
 * </p>
 *
 * @author duo.tao
 * @since 2025-06-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel(value = "奖品表对象")
public class Prize extends BaseEntity {

    @ApiModelProperty(value = "奖品名称")
    private String name;

    @ApiModelProperty(value = "库存")
    private String stock;

    @ApiModelProperty(value = "中奖概率(0-1)")
    private BigDecimal probability;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "分组Id")
    private String groupId;



}

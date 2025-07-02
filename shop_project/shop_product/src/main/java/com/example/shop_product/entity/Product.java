package com.example.shop_product.entity;

import com.example.shop_common.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author duo.tao
 * @since 2025-06-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Product对象", description="商品表")
public class Product extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品编号")
    private String code;

    @ApiModelProperty(value = "商品标题")
    private String name;

    @ApiModelProperty(value = "商品型号")
    private String spec;

    @ApiModelProperty(value = "可选规格")
    private String productSize;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "租户id")
    private String tenantId;


}

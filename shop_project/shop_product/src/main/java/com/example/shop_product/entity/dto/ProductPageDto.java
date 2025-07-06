package com.example.shop_product.entity.dto;

import com.example.shop_product.entity.Product;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class ProductPageDto extends Product {

    private long current;

    private long size;

}

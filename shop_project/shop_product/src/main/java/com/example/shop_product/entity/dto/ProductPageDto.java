package com.example.shop_product.entity.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.example.shop_product.entity.Product;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

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
public class ProductPageDto extends Product implements IPage<Product> {

    private long total;

    private long size;

    private long current;

    private List<Product> records;

    private List<OrderItem> orders;

    @Override
    public List<OrderItem> orders() {
        return orders;
    }

    @Override
    public List<Product> getRecords() {
        return records;
    }

}

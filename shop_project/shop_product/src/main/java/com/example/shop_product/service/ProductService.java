package com.example.shop_product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shop_product.entity.Product;
import com.example.shop_common.common.service.CrudService;
import com.example.shop_product.entity.dto.ProductPageDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author duo.tao
 * @since 2025-06-30
 */
public interface ProductService extends CrudService<Product> {

    Product saveProduct(Product product);

    List<String> getTopProducts( int count);

    IPage<Product> findListByPage(ProductPageDto page);
}

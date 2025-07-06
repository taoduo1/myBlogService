package com.example.shop_product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shop_common.common.service.CrudServiceImpl;
import com.example.shop_common.utils.DataUtil;
import com.example.shop_product.common.RedisZSetComponent;
import com.example.shop_product.entity.Product;
import com.example.shop_product.entity.dto.ProductPageDto;
import com.example.shop_product.mapper.ProductMapper;
import com.example.shop_product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author duo.tao
 * @since 2025-06-30
 */
@Service
public class ProductServiceImpl extends CrudServiceImpl<ProductMapper, Product> implements ProductService {

    private static final String HOT_PRODUCTS_KEY = "hot_products";


    @Autowired
    private RedisZSetComponent redisZSetComponent;

    @Override
    public Product saveProduct(Product product) {
        save(product);
        return product;
    }

    @Override
    public Product get(Serializable id) {
        Product data = super.get(id);
        if (DataUtil.isNull(data)) return null;
        redisZSetComponent.incrScore(HOT_PRODUCTS_KEY, data.getName(), 1D);
        return data;
    }

    @Override
    public List<String> getTopProducts(int count) {
        Set<String> valData = redisZSetComponent.revRange(HOT_PRODUCTS_KEY, 0, count);
        if (DataUtil.isNullOrEmpty(valData)) return null;
        return valData.stream().toList();
    }

    @Override
    public IPage<Product> findListByPage(ProductPageDto dto) {
        Page<Product> page = new Page<>(dto.getCurrent(), dto.getSize());
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        if (DataUtil.notNullOrEmpty(dto.getName())){
            wrapper.like("name",dto.getName());
        }
        IPage<Product> data = dao.selectPage(page, wrapper);
        return data;
    }

}

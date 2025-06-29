package com.example.shop_product.service.impl;

import com.example.shop_common.common.service.CrudServiceImpl;
import com.example.shop_common.entity.base.ConfigTable;
import com.example.shop_product.mapper.ConfigTableMapper;
import com.example.shop_product.service.ConfigTableService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户配置信息表 服务实现类
 * </p>
 *
 * @author duo.tao
 * @since 2025-06-29
 */
@Service
public class ConfigTableServiceImpl extends CrudServiceImpl<ConfigTableMapper, ConfigTable> implements ConfigTableService {

}

package com.example.shop_user.service.impl;

import com.example.shop_user.entity.TenantConfig;
import com.example.shop_user.mapper.TenantConfigMapper;
import com.example.shop_user.service.ITenantConfigService;
import com.example.shop_common.common.service.CrudServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户配置信息表 服务实现类
 * </p>
 *
 * @author duo.tao
 * @since 2022-06-21
 */
@Service
public class TenantConfigServiceImpl extends CrudServiceImpl<TenantConfigMapper, TenantConfig> implements ITenantConfigService {

}

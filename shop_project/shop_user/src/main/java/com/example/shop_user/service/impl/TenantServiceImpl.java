package com.example.shop_user.service.impl;

import com.example.shop_user.entity.Tenant;
import com.example.shop_user.mapper.TenantMapper;
import com.example.shop_user.service.ITenantService;
import com.example.shop_common.common.service.CrudServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author duo.tao
 * @since 2022-06-21
 */
@Service
public class TenantServiceImpl extends CrudServiceImpl<TenantMapper, Tenant> implements ITenantService {

}

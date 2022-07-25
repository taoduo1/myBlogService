package com.example.shop_user.service;

import com.example.shop_common.common.service.CrudService;
import com.example.shop_user.dto.RegisterTenantDto;
import com.example.shop_user.entity.Tenant;

/**
 * <p>
 * 租户表 服务类
 * </p>
 *
 * @author duo.tao
 * @since 2022-06-21
 */
public interface ITenantService extends CrudService<Tenant> {

    void registerTenant(RegisterTenantDto user);

}

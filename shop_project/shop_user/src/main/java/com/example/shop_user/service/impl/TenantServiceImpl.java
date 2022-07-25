package com.example.shop_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shop_common.common.dto.CoreException;
import com.example.shop_common.common.enums.user.TenantTypeEnum;
import com.example.shop_common.common.service.CrudServiceImpl;
import com.example.shop_common.utils.DataUtil;
import com.example.shop_user.dto.RegisterTenantDto;
import com.example.shop_user.entity.Tenant;
import com.example.shop_user.mapper.TenantMapper;
import com.example.shop_user.service.ITenantService;
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


    @Override
    public void registerTenant(RegisterTenantDto tenantDto) {
        if (DataUtil.isNull(tenantDto.getTenantType()) || (TenantTypeEnum.USER_RULE_TENANT.notMatch(tenantDto.getTenantType()) && TenantTypeEnum.USER_REGISTER_TENANT.notMatch(tenantDto.getTenantType()) && TenantTypeEnum.USER_GROUP_TENANT.notMatch(tenantDto.getTenantType()))) {
            throw new CoreException("租户类型错误");
        }
        //校验是否已经存在管理租户，如果管理租户已经存在，则不再创建管理租户
        if (TenantTypeEnum.USER_RULE_TENANT.match(tenantDto.getTenantType()) && countByCondition(new QueryWrapper<Tenant>().eq("type", TenantTypeEnum.USER_RULE_TENANT.getIndex())) > 0) {
            throw new CoreException("管理租户已存在");
        }
        //校验是否已经存在自注册租户，如果自注册租户已经存在，则不再创建自注册租户
        if (TenantTypeEnum.USER_REGISTER_TENANT.match(tenantDto.getTenantType()) && countByCondition(new QueryWrapper<Tenant>().eq("type", TenantTypeEnum.USER_REGISTER_TENANT.getIndex())) > 0) {
            throw new CoreException("自注册租户已存在");
        }
        //集团租户只判断code是否重复
        if (TenantTypeEnum.USER_GROUP_TENANT.match(tenantDto.getTenantType()) && countByCondition(new QueryWrapper<Tenant>().eq("code", tenantDto.getCode()).eq("type", TenantTypeEnum.USER_GROUP_TENANT.getIndex())) > 0) {
            throw new CoreException("该编号租户已存在");
        }
        save(new Tenant() {{
            setCode(tenantDto.getCode());
            setName(tenantDto.getName());
            setType(tenantDto.getTenantType());
        }});
    }
}

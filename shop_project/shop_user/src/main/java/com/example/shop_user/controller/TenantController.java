package com.example.shop_user.controller;


import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.utils.ResultUtil;
import com.example.shop_user.dto.RegisterTenantDto;
import com.example.shop_user.entity.Tenant;
import com.example.shop_user.service.TenantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 租户表 前端控制器
 * </p>
 *
 * @author duo.tao
 * @since 2022-06-21
 */
@RestController
@RequestMapping("/user/tenant")

public class TenantController {

    @Resource
    private TenantService tenantService;

    @ApiOperation(value = "创建租户", notes = "创建租户接口")
    @PostMapping(value = "/registerTenant")
    public ActionResult<Tenant> registerTenant(@Valid @RequestBody RegisterTenantDto registerTenantDto) {
        tenantService.registerTenant(registerTenantDto);
        return ResultUtil.ok();
    }
}

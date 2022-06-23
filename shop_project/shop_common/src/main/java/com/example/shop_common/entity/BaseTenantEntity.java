package com.example.shop_common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author duo.tao
 * @Description: 基础实体类
 * @date 2022-06-13 23:14
 */
@Data
public class BaseTenantEntity extends BaseEntity {

    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

}

package com.example.shop_common.entity.base;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="配置表", description="配置表")
@TableName("config_table")
public class ConfigTable extends BaseTenantEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String configType;

    @ApiModelProperty(value = "用户名")
    private String configKey;

    @ApiModelProperty(value = "用户名")
    private String configValue;


}

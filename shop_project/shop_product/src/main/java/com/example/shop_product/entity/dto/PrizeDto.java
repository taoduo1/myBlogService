package com.example.shop_product.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 奖品表
 * </p>
 *
 * @author duo.tao
 * @since 2025-06-29
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "奖品表Dto")
public class PrizeDto {

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "分组Id")
    private String groupId;

}

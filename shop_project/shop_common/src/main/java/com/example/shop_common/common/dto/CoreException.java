package com.example.shop_common.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author duo.tao
 * @Description: 自定义全局异常类
 * @date 2022-06-13 23:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "自定义全局异常类")
public class CoreException extends RuntimeException {

    @ApiModelProperty(value = "异常状态码")
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param message
     * @param code
     */
    public CoreException(String message, Integer code) {
        super(message);
        this.code = code;
    }


    public CoreException(String message) {
        super(message);
        this.code = 500;
    }
}

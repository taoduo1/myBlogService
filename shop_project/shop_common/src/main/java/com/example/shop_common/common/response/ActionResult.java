package com.example.shop_common.common.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *返回结果类统一封装
 */
@Data
public class ActionResult<T> implements Serializable {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "数据对象")
    private T data;

    @ApiModelProperty(value = "错误信息列表")
    private List<ErrorInfo> errorInfo;
}


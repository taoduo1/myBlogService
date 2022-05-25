package com.example.shop_common.common.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorInfo {

    @ApiModelProperty(value = "错误信息编号")
    private Integer code;

    @ApiModelProperty(value = "错误信息")
    private String message;

    public ErrorInfo(String message) {
        this.message = message;
        this.code = 500;
    }

}

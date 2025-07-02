package com.example.shop_product.controller;


import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.utils.ResultUtil;
import com.example.shop_product.entity.Product;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 消息队列功能
 */
@RestController
@RequestMapping("/product/queue")
public class QueueController {

    @ApiOperation(value = "商品下单", notes = "商品下单点对点")
    @PostMapping("/saveProduct")
    public ActionResult<Product> saveProduct(@Valid @RequestBody Product product){
        return ResultUtil.ok();
    }

    @ApiOperation(value = "删除商品", notes = "删除商品-工作队列")
    @PostMapping("/leteProduct")
    public ActionResult<Product> deleteProduct(@Valid @RequestBody Product product){
        return ResultUtil.ok();
    }

    @ApiOperation(value = "用户通知", notes = "用户通知-发布/订阅模式")
    @PostMapping("/noticeUser")
    public ActionResult<Product> noticeUser(@Valid @RequestBody Product product){
        return ResultUtil.ok();
    }
    @ApiOperation(value = "通知多个服务", notes = "通知多个服务-通配符模式")
    @PostMapping("/noticeAll")
    public ActionResult<Product> noticeAll(@Valid @RequestBody Product product){
        return ResultUtil.ok();
    }

    @ApiOperation(value = "数据同步", notes = "数据同步-RPC通信模式")
    @PostMapping("/datasync")
    public ActionResult<Product> datasync(@Valid @RequestBody Product product){
        return ResultUtil.ok();
    }

    @ApiOperation(value = "商品数量扣减", notes = "商品数量扣减-发布确认模式")
    @PostMapping("/productNumChange")
    public ActionResult<Product> productNumChange(@Valid @RequestBody Product product){
        return ResultUtil.ok();
    }
}

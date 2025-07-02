package com.example.shop_product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.utils.ResultUtil;
import com.example.shop_product.entity.Product;
import com.example.shop_product.entity.dto.ProductPageDto;
import com.example.shop_product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author duo.tao
 * @since 2025-06-30
 */
@RestController
@RequestMapping("/product")
public class ProductController {


    @Resource
    private ProductService prizeService;

    @ApiOperation(value = "增加商品", notes = "增加商品")
    @PostMapping("/savePrize")
    public ActionResult<Product> savePrize(@Valid @RequestBody Product product){
        return ResultUtil.ok(prizeService.saveProduct(product));
    }

    @ApiOperation(value = "根据id获取某个商品", notes = "根据id获取某个商品")
    @PostMapping("/deleteById")
    public ActionResult<Void> deleteById(@Valid @RequestBody Product product){
        prizeService.delete(product.getId());
        return ResultUtil.ok();
    }

    @ApiOperation(value = "商品信息更新", notes = "商品信息更新")
    @PostMapping("/updatePrize")
    public ActionResult<Product> updatePrize(@Valid @RequestBody Product product){
        return ResultUtil.ok(prizeService.saveProduct(product));
    }

    @ApiOperation(value = "根据id获取某个商品", notes = "根据id获取某个商品")
    @GetMapping("/findById/{id}")
    public ActionResult<Product> findById(@PathVariable Integer id){
        Product product = prizeService.get(id);
        return ResultUtil.ok(product);
    }

    @ApiOperation(value = "返回商品分页列表", notes = "返回商品分页列表")
    @PostMapping("/findListByPage")
    public ActionResult<IPage<Product>> findListByPage(@Valid @RequestBody ProductPageDto page){
        return ResultUtil.ok(prizeService.findListByPage(page));
    }


    @ApiOperation(value = "获取搜索排行榜-从大到小", notes = "获取排行榜")
    @GetMapping("/top/{count}")
    public ActionResult<List<String>> getTopProducts(@PathVariable int count) {
        return ResultUtil.ok(prizeService.getTopProducts(count));
    }

}

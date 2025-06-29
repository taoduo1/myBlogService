package com.example.shop_product.controller;


import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.utils.ResultUtil;
import com.example.shop_product.entity.Prize;
import com.example.shop_product.entity.dto.PrizeDto;
import com.example.shop_product.service.PrizeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 奖品表
 * </p>
 *
 * @author duo.tao
 * @since 2025-06-29
 */
@Slf4j
@RestController
@RequestMapping("/product/prize")
public class PrizeController {

    @Resource
    private PrizeService prizeService;

    @ApiOperation(value = "增加奖品", notes = "增加奖品")
    @PostMapping("/savePrize")
    public ActionResult<Prize> savePrize(@Valid @RequestBody Prize prize){
        return ResultUtil.ok(prizeService.savePrize(prize));
    }

    @ApiOperation(value = "根据id获取某个用户", notes = "根据id获取某个用户")
    @PostMapping("/deleteById")
    public ActionResult<Void> deleteById(@Valid @RequestBody Prize prize){
        prizeService.delete(prize.getId());
        return ResultUtil.ok();
    }

    @ApiOperation(value = "奖品信息更新", notes = "奖品信息更新")
    @PostMapping("/updatePrize")
    public ActionResult<Prize> updatePrize(@Valid @RequestBody Prize prize){
        return ResultUtil.ok(prizeService.savePrize(prize));
    }

    @ApiOperation(value = "根据id获取某个用户", notes = "根据id获取某个用户")
    @GetMapping("/findById/{id}")
    public ActionResult<Prize> findById(@PathVariable Integer id){
        Prize prize = prizeService.get(id);
        return ResultUtil.ok(prize);
    }


    @ApiOperation(value = "抽奖初始化", notes = "抽奖初始化")
    @PostMapping("/initPrizes")
    public ActionResult<Void> initPrizes(@Valid @RequestBody PrizeDto prize){
        prizeService.initPrizes(prize);
        return ResultUtil.ok();
    }

    @ApiOperation(value = "用户抽奖", notes = "用户抽奖")
    @PostMapping("/drawPrizes")
    public ActionResult<Prize> drawPrizes(@Valid @RequestBody PrizeDto prize){
        Prize p = prizeService.drawPrizes(prize);
        return ResultUtil.ok(p);
    }




}

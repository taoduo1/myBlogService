package com.example.shop_user.controller;

import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.utils.ResultUtil;
import com.example.shop_user.dto.Order;
import com.example.shop_user.dto.RuleResult;
import com.example.shop_user.service.DiscountRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private DiscountRuleService ruleService;

    @PostMapping("/apply-discount")
    public ActionResult<RuleResult> applyDiscount(@RequestBody Order order) {
        RuleResult result = ruleService.applyDiscount(order);
        return ResultUtil.ok(result);
    }
}

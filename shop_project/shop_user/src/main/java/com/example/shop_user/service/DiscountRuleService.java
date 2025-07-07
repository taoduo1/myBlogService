package com.example.shop_user.service;

import com.example.shop_user.dto.Order;
import com.example.shop_user.dto.RuleResult;

public interface DiscountRuleService {

    RuleResult applyDiscount(Order order);
}

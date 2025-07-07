package com.example.shop_user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleResult {

    private String ruleName;
    private BigDecimal discountRate;


}

package com.example.shop_user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private String customerType; // VIP/NORMAL
    private BigDecimal amount;
    private BigDecimal discount = BigDecimal.ZERO;
}

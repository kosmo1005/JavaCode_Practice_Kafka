package com.kulushev.Shipping.dto;

import com.kulushev.Shipping.enums.OrderStatus;

import java.math.BigDecimal;

public record OrderReqDto(
        Long id,
        String userId,
        OrderStatus status,
        String goodName,
        BigDecimal price,
        boolean isPaid
) {
}
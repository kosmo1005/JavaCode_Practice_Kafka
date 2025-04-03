package com.kulushev.Orders.dto;

import com.kulushev.Orders.enums.OrderStatus;

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
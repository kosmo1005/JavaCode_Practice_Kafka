package com.kulushev.Payment.dto;

import com.kulushev.Payment.enums.OrderStatus;

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
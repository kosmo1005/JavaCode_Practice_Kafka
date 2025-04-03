package com.kulushev.Notifications.dto;

import com.kulushev.Notifications.enums.OrderStatus;

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
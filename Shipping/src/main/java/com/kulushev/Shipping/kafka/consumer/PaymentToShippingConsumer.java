package com.kulushev.Shipping.kafka.consumer;

import com.kulushev.Shipping.dto.OrderReqDto;
import com.kulushev.Shipping.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentToShippingConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "payed_orders", groupId = "shipping-listen-payment-group",  concurrency = "3", containerFactory = "orderReqDtoListenerFactory")
    public void handleSentOrder(OrderReqDto dto) {
        orderService.shipOrder(dto);
    }
}

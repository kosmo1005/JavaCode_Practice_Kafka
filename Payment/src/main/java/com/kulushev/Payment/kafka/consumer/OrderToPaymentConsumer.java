package com.kulushev.Payment.kafka.consumer;

import com.kulushev.Payment.dto.OrderReqDto;
import com.kulushev.Payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderToPaymentConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "new_orders", groupId = "payment-listen-order-group",  concurrency = "3", containerFactory = "orderReqDtoListenerFactory")
    public void handleSentOrder(OrderReqDto dto) {
        orderService.payOrder(dto);
    }
}

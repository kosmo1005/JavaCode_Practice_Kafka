package com.kulushev.Orders.kafka.consumer;

import com.kulushev.Orders.dto.OrderReqDto;
import com.kulushev.Orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingToOrderConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "sent_orders", groupId = "orders-listen-ship-group", concurrency = "3", containerFactory = "orderReqDtoListenerFactory")
    public void handleSentOrder(OrderReqDto dto) {
        orderService.updateOrder(dto);
    }
}

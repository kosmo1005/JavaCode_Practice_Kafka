package com.kulushev.Notifications.kafka.consumer;

import com.kulushev.Notifications.dto.OrderReqDto;
import com.kulushev.Notifications.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingToNotificationConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "sent_orders", groupId = "notification-listen-shipping-group",  concurrency = "3", containerFactory = "orderReqDtoListenerFactory")
    public void handleSentOrder(OrderReqDto dto) {
        orderService.notifyUserAboutShipOrder(dto);
    }
}

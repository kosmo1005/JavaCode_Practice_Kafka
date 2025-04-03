package com.kulushev.Orders.kafka.producer;

import com.kulushev.Orders.dto.OrderRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderToPaymentProducer {
    private final KafkaTemplate<String, OrderRespDto> kafkaTemplate;
    public void sendOrderToPayment(OrderRespDto dto) {
        kafkaTemplate.send("new_orders", dto);
        log.info("Заказ отправлен в Payment: {}", dto);
    }
}

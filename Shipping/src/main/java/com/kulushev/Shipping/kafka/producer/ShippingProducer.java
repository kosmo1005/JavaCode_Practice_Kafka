package com.kulushev.Shipping.kafka.producer;

import com.kulushev.Shipping.dto.OrderRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingProducer {

    private final KafkaTemplate<String, OrderRespDto> kafkaTemplate;

    public void sendOrderToPayment(OrderRespDto dto) {
        kafkaTemplate.send("sent_orders", dto);
        log.info("Отправка заказа опубликована: {}", dto);

    }
}

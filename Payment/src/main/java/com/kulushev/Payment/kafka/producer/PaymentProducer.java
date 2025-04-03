package com.kulushev.Payment.kafka.producer;

import com.kulushev.Payment.dto.OrderRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, OrderRespDto> kafkaTemplate;

    public void sendOrderToPayment(OrderRespDto dto) {
        kafkaTemplate.send("payed_orders", dto);
        log.info("Оплата заказа опубликована: {}", dto);

    }
}

package com.kulushev.Payment.service;

import com.kulushev.Payment.dto.OrderReqDto;
import com.kulushev.Payment.dto.OrderRespDto;
import com.kulushev.Payment.enums.OrderStatus;
import com.kulushev.Payment.kafka.producer.PaymentProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final PaymentProducer paymentProducer;

    public void payOrder(OrderReqDto dto) {

        var orderRespDto = new OrderRespDto(
                dto.id(),
                dto.userId(),
                OrderStatus.PAID,
                dto.goodName(),
                dto.price(),
                true
        );
        log.info("Заказ оплачен: {}", orderRespDto);
        paymentProducer.sendOrderToPayment(orderRespDto);
    }
}

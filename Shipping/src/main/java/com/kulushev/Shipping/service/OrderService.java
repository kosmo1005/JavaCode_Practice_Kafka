package com.kulushev.Shipping.service;

import com.kulushev.Shipping.dto.OrderReqDto;
import com.kulushev.Shipping.dto.OrderRespDto;
import com.kulushev.Shipping.enums.OrderStatus;
import com.kulushev.Shipping.kafka.producer.ShippingProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final ShippingProducer shippingProducer;

    public void shipOrder(OrderReqDto dto) {

        var orderRespDto = new OrderRespDto(
                dto.id(),
                dto.userId(),
                OrderStatus.SHIPPED,
                dto.goodName(),
                dto.price(),
                dto.isPaid()
        );

        log.info("Заказ упакован и отправлен на доставку: {}", orderRespDto);
        shippingProducer.sendOrderToPayment(orderRespDto);
    }
}

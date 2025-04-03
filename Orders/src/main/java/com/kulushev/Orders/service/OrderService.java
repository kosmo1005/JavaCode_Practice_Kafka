package com.kulushev.Orders.service;

import com.kulushev.Orders.dto.OrderEvent;
import com.kulushev.Orders.dto.OrderReqDto;
import com.kulushev.Orders.dto.OrderRespDto;
import com.kulushev.Orders.enums.OrderStatus;
import com.kulushev.Orders.exception.notFound.OrderNotFoundException;
import com.kulushev.Orders.kafka.producer.OrderToPaymentProducer;
import com.kulushev.Orders.repository.OrderRepository;
import com.kulushev.Orders.transformer.OrderTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repo;
    private final OrderTransformer t;
    private final OrderToPaymentProducer orderToPaymentProducer;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public OrderRespDto createOrder(OrderReqDto dto) {

        var entity = t.dtoToEntity(dto);
        entity.setStatus(OrderStatus.NEW);
        entity.setPaid(false);
        var savedOrder = repo.save(entity);

        var orderRespDto = t.entityToDto(savedOrder);
        eventPublisher.publishEvent(new OrderEvent(orderRespDto));
        return orderRespDto;
    }

    @Transactional
    public OrderRespDto updateOrder(OrderReqDto dto) {
        if (!orderExists(dto.id())) {
            throw new OrderNotFoundException("Order not found");
        }

        var order = t.dtoToEntity(dto);
        var updatedOrder = repo.save(order);
        var orderRespDto = t.entityToDto(updatedOrder);
        log.info("Заказ обновлен: {}.", orderRespDto);
        return orderRespDto;
    }

    @Transactional
    public void deleteOrderById(Long id) {
        if (repo.findById(id).isEmpty()) {
            throw new OrderNotFoundException("Order not found");
        }
        repo.deleteById(id);
    }

    @Transactional
    public boolean orderExists(Long id) {
        return repo.findById(id).isPresent();
    }
}

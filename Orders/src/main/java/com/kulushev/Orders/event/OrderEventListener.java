package com.kulushev.Orders.event;

import com.kulushev.Orders.dto.OrderEvent;
import com.kulushev.Orders.kafka.producer.OrderToPaymentProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final OrderToPaymentProducer producer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Retryable(
            retryFor = { KafkaException.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void handleOrderCreated(OrderEvent event) {
        log.info("Заказ создан: {}.", event.order());
        producer.sendOrderToPayment(event.order());
    }

    @Recover
    public void recover(KafkaException e, OrderEvent event) {
        log.error("Не удалось отправить заказ после всех попыток: {}. Ошибка: {}", event.order(), e.getMessage());
    }
}

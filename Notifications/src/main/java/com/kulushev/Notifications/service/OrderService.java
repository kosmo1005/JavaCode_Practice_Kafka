package com.kulushev.Notifications.service;

import com.kulushev.Notifications.dto.OrderReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    public String notifyUserAboutShipOrder(OrderReqDto dto) {
        StringBuilder notify = new StringBuilder();
        notify.append("Ваш заказ № ");
        notify.append(dto.id());
        notify.append(" - ");
        notify.append(dto.goodName());
        notify.append(" сформирован и отправлен в доставку.");

        log.info("Отправление уведомления пользователю: {}", notify);
        return notify.toString();
    }
}

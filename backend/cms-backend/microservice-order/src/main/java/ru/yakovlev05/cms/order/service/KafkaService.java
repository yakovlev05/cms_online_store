package ru.yakovlev05.cms.order.service;

import ru.yakovlev05.cms.order.entity.Order;

public interface KafkaService {
    void sendOrderValidationInputEvent(Order order);
    void sendOrderPaymentCreateEvent(Order order);
}

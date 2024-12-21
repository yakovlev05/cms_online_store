package ru.yakovlev05.cms.payment.service;

import ru.yakovlev05.cms.payment.entity.Payment;

public interface KafkaService {
    void sendOrderPaymentResultEvent(Payment payment);
}

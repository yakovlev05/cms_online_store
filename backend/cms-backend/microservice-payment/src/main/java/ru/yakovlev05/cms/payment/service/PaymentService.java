package ru.yakovlev05.cms.payment.service;

import ru.yakovlev05.cms.core.event.OrderPaymentCreateEvent;
import ru.yakovlev05.cms.payment.dto.ConfirmationUrlDto;
import ru.yakovlev05.cms.payment.dto.PaymentEventDto;
import ru.yakovlev05.cms.payment.entity.Payment;

public interface PaymentService {
    void createPayment(OrderPaymentCreateEvent request);

    void processEvent(PaymentEventDto request);

    ConfirmationUrlDto getConfirmationUrl(String orderId);

    Payment getByPaymentId(String paymentId);
}

package ru.yakovlev05.cms.payment.service;

import ru.yakovlev05.cms.payment.dto.PaymentResponseDto;

public interface YookassaService {
    PaymentResponseDto createPayment(String amount, String description);
}

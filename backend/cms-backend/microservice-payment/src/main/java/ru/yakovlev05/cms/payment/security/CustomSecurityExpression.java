package ru.yakovlev05.cms.payment.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.payment.dto.PaymentEventDto;
import ru.yakovlev05.cms.payment.entity.Payment;
import ru.yakovlev05.cms.payment.service.PaymentService;

@RequiredArgsConstructor
@Component("cse")
public class CustomSecurityExpression {

    private final PaymentService paymentService;

    public boolean isYookassa(PaymentEventDto request) {
        Payment payment = paymentService.getByPaymentId(request.getObject().getId());
        return request.getObject().getMetadata().getVerifyKey().equals(payment.getVerifyKey());
    }
}

package ru.yakovlev05.cms.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakovlev05.cms.payment.dto.ConfirmationUrlDto;
import ru.yakovlev05.cms.payment.service.PaymentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{order-id}")
    public ConfirmationUrlDto getConfirmationUrl(@PathVariable(name = "order-id") String orderId) {
        return paymentService.getConfirmationUrl(orderId);
    }
}

package ru.yakovlev05.cms.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakovlev05.cms.payment.dto.PaymentEventDto;
import ru.yakovlev05.cms.payment.service.PaymentService;
import ru.yakovlev05.cms.payment.service.YookassaService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment/webhook")
public class WebhookController {

    private final YookassaService yookassaService;
    private final PaymentService paymentService;


    @PreAuthorize("@cse.isYookassa(#request)")
    @PostMapping("/event")
    public void paymentSucceeded(@RequestBody PaymentEventDto request) {
        paymentService.processEvent(request);
    }
}

package ru.yakovlev05.cms.payment.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.core.event.OrderPaymentCreateEvent;
import ru.yakovlev05.cms.payment.service.PaymentService;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "order.payment.create")
public class OrderPaymentCreateEventListener {

    private final PaymentService paymentService;

    @KafkaHandler
    public void handleOrderPaymentCreateEvent(OrderPaymentCreateEvent event) {
        log.info("Received order payment create event: {}", event);

        paymentService.createPayment(event);
    }

}

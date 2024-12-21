package ru.yakovlev05.cms.order.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.core.event.OrderPaymentResultEvent;
import ru.yakovlev05.cms.order.entity.Order;
import ru.yakovlev05.cms.order.entity.OrderStatus;
import ru.yakovlev05.cms.order.entity.PaymentStatus;
import ru.yakovlev05.cms.order.service.OrderService;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "order.payment.result")
public class OrderPaymentResultListener {

    private final OrderService orderService;

    @KafkaHandler
    public void handleOrderPaymentResultEvent(OrderPaymentResultEvent event) {
        log.info("Received order payment result event: {}", event);
        Order order = orderService.getById(event.getOrderId());

        if (event.getPaymentStatus().equals(OrderPaymentResultEvent.PaymentResult.SUCCEED)) {
            order.setStatus(OrderStatus.PAID);
            order.getPaymentInfo().setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS);
        } else {
            order.setStatus(OrderStatus.CANCELLED);
            order.getPaymentInfo().setPaymentStatus(PaymentStatus.PAYMENT_FAILED);
        }

        orderService.save(order);
    }
}

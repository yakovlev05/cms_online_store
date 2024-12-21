package ru.yakovlev05.cms.order.listener;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.core.event.OrderValidationResultEvent;
import ru.yakovlev05.cms.core.event.OrderValidationStatus;
import ru.yakovlev05.cms.order.entity.Order;
import ru.yakovlev05.cms.order.entity.OrderStatus;
import ru.yakovlev05.cms.order.entity.Product;
import ru.yakovlev05.cms.order.service.OrderService;
import ru.yakovlev05.cms.order.service.ProductService;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "order.validation.result")
public class OrderValidationResultListener {

    private final OrderService orderService;
    private final ProductService productService;

    @Transactional
    @KafkaHandler
    public void handleOrderValidationResultEvent(OrderValidationResultEvent event) {
        log.info("Received order validation result event: {}", event);

        Order order = orderService.getById(event.getOrderId());
        if (event.getValidationStatus().equals(OrderValidationStatus.ERROR)) {
            order.setStatus(OrderStatus.INVALID_VALIDATION);
            orderService.save(order);
            return;
        }

        for (OrderValidationResultEvent.Product productEvent : event.getProducts()) {
            Product product = productService.getById(productEvent.getProductOrderId());
            product.setName(productEvent.getName());
            product.setPrice(productEvent.getPrice());
            productService.save(product);
        }

        order.setStatus(OrderStatus.PLACED);
        order.setProductsCost(event.getCost());

        orderService.save(order);
    }
}

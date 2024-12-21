package ru.yakovlev05.cms.order.listener;

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

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "order.validation.result")
public class OrderValidationResultListener {

    private final OrderService orderService;

    @KafkaHandler
    public void handleOrderValidationResultEvent(OrderValidationResultEvent event) {
        log.info("Received order validation result event: {}", event);

        Order order = orderService.getById(event.getOrderId());
        if (event.getValidationStatus().equals(OrderValidationStatus.ERROR)) {
            order.setStatus(OrderStatus.INVALID_VALIDATION);
            orderService.save(order);
            return;
        }

        List<Product> productList = new ArrayList<>();

        for (OrderValidationResultEvent.Product productEvent : event.getProducts()) {
            productList.add(Product.builder()
                    .id(productEvent.getProductOrderId())
                    .productId(productEvent.getOriginalId())
                    .name(productEvent.getName())
                    .count(productEvent.getCount())
                    .price(productEvent.getPrice())
                    .order(order)
                    .build());
        }

        order.setProducts(productList);

        orderService.save(order);
    }
}

package ru.yakovlev05.cms.catalog.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.catalog.entity.Product;
import ru.yakovlev05.cms.catalog.exception.BadRequestException;
import ru.yakovlev05.cms.catalog.service.KafkaService;
import ru.yakovlev05.cms.catalog.service.ProductService;
import ru.yakovlev05.cms.core.event.OrderValidationInputEvent;
import ru.yakovlev05.cms.core.event.OrderValidationResultEvent;
import ru.yakovlev05.cms.core.event.OrderValidationStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "order.validation.input")
public class OrderValidationInputListener {

    private final ProductService productService;
    private final KafkaService kafkaService;

    @KafkaHandler
    public void handleOrderValidationInputEvent(OrderValidationInputEvent event) {
        log.info("Received order validation input event: {}", event);

        BigDecimal totalCost = BigDecimal.ZERO;
        List<OrderValidationResultEvent.Product> productsResult = new ArrayList<>();
        try {
            for (OrderValidationInputEvent.Product product : event.getProductIds()) {
                Product productInfo = productService.getProductById(product.getOriginalProductId());

                totalCost = totalCost.add(productInfo.getPrice());
                productsResult.add(OrderValidationResultEvent.Product.builder()
                        .productOrderId(product.getId())
                        .originalId(product.getOriginalProductId())
                        .name(productInfo.getName())
                        .price(productInfo.getPrice())
                        .build());
            }

            kafkaService.sendOrderValidationResultEvent(OrderValidationResultEvent.builder()
                    .orderId(event.getOrderId())
                    .validationStatus(OrderValidationStatus.OK)
                    .cost(totalCost)
                    .products(productsResult)
                    .build());

        } catch (BadRequestException e) {
            kafkaService.sendOrderValidationResultEvent(OrderValidationResultEvent.builder()
                    .validationStatus(OrderValidationStatus.ERROR)
                    .build());
        }

    }
}

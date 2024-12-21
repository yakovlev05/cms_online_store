package ru.yakovlev05.cms.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.core.event.OrderPaymentCreateEvent;
import ru.yakovlev05.cms.core.event.OrderValidationInputEvent;
import ru.yakovlev05.cms.order.entity.Order;
import ru.yakovlev05.cms.order.props.KafkaProducerProperties;
import ru.yakovlev05.cms.order.service.KafkaService;
import ru.yakovlev05.cms.order.service.ProductService;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaProducerProperties props;

    private final ProductService productService;


    @Override
    public void sendOrderValidationInputEvent(Order order) {
        OrderValidationInputEvent event = OrderValidationInputEvent.builder()
                .orderId(order.getId())
                .productIds(productService.getProductsByOrder(order).stream()
                        .map(product -> new OrderValidationInputEvent.Product(
                                product.getId(),
                                product.getProductId(),
                                product.getCount()))
                        .toList())
                .build();

        log.info("Send order to topic {}: {}", props.getOrderValidationInputTopicName(), event);

        kafkaTemplate.send(props.getOrderValidationInputTopicName(), event.getOrderId(), event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Send order to topic {} failed", props.getOrderValidationInputTopicName(), exception);
                    } else {
                        log.info("Send order to topic {} success: {}", props.getOrderValidationInputTopicName(), result.getRecordMetadata());
                    }
                });
    }

    @Override
    public void sendOrderPaymentCreateEvent(Order order) {
        OrderPaymentCreateEvent event = OrderPaymentCreateEvent.builder()
                .orderId(order.getId())
                .amount(order.getPaymentInfo().getFinalSum())
                .build();

        log.info("Send order payment to topic {}: {}", props.getOrderPaymentCreateTopicName(), event);
        kafkaTemplate.send(props.getOrderPaymentCreateTopicName(), event.getOrderId(), event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Send order payment to topic {} failed", props.getOrderPaymentCreateTopicName(), exception);
                    } else {
                        log.info("Send order payment to topic {} success", props.getOrderPaymentCreateTopicName());
                    }
                });
    }
}

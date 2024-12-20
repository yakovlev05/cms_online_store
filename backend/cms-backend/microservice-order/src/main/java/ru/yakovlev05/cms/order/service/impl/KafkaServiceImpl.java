package ru.yakovlev05.cms.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.core.event.OrderValidationInputEvent;
import ru.yakovlev05.cms.order.entity.Order;
import ru.yakovlev05.cms.order.props.KafkaProducerProperties;
import ru.yakovlev05.cms.order.service.KafkaService;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaProducerProperties props;


    @Override
    public void sendOrderValidationInputEvent(Order order) {
        OrderValidationInputEvent event = OrderValidationInputEvent.builder()
                .orderId(order.getId())
                .productIds(order.getProducts().stream()
                        .map(product -> new OrderValidationInputEvent.Product(product.getId(), product.getProductId()))
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
}

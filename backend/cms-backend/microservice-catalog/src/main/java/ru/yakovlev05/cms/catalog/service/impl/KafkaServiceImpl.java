package ru.yakovlev05.cms.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.entity.Product;
import ru.yakovlev05.cms.catalog.props.KafkaProducerProperties;
import ru.yakovlev05.cms.catalog.service.KafkaService;
import ru.yakovlev05.cms.catalog.service.S3Service;
import ru.yakovlev05.cms.core.event.EventType;
import ru.yakovlev05.cms.core.event.OrderValidationResultEvent;
import ru.yakovlev05.cms.core.event.ProductEvent;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaProducerProperties props;

    private final S3Service s3Service;


    @Override
    public void sendProductEvent(Product product, boolean isAvailable, EventType eventType) {
        ProductEvent event = ProductEvent.builder()
                .eventType(eventType)
                .id(product.getId())
                .name(product.getName())
                .urlName(product.getUrlName())
                .description(product.getDescription())
                .price(product.getPrice())
                .priceDiscount(product.getPriceDiscount())
                .mainPhotoUrl(s3Service.getCustomUrl(product.getMainPhoto().getFileName()))
                .isAvailable(isAvailable)
                .build();

        log.info("Send data to topic product: {}", event);

        kafkaTemplate.send(props.getProductTopicName(), String.valueOf(event.getId()), event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Send data to topic {} failed", props.getProductTopicName(), exception);
                    } else {
                        log.info("Send data to topic {} success: {}", props.getProductTopicName(), result.getRecordMetadata());
                    }
                });
    }

    @Override
    public void sendOrderValidationResultEvent(OrderValidationResultEvent event) {
        log.info("Send order.validation.result event {}", event);

        kafkaTemplate.send(props.getOrderValidationResultTopicName(), event.getOrderId(), event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Send order.validation.result event {} failed", event, exception);
                    } else {
                        log.info("Send order.validation.result event {} success: {}", event, result);
                    }
                });

    }
}

package ru.yakovlev05.cms.payment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.core.event.OrderPaymentResultEvent;
import ru.yakovlev05.cms.payment.entity.Payment;
import ru.yakovlev05.cms.payment.props.KafkaProducerProperties;
import ru.yakovlev05.cms.payment.service.KafkaService;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaProducerProperties props;


    @Override
    public void sendOrderPaymentResultEvent(Payment payment) {
        OrderPaymentResultEvent event = OrderPaymentResultEvent.builder()
                .orderId(payment.getOrderId())
                .paymentStatus(OrderPaymentResultEvent.PaymentResult.SUCCEED)
                .build();

        log.info("Send payment result to topic {}: {}", props.getOrderPaymentResultTopicName(), event);

        kafkaTemplate.send(props.getOrderPaymentResultTopicName(), payment.getOrderId(), event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Send order to topic {} failed", props.getOrderPaymentResultTopicName(), exception);
                    } else {
                        log.info("Send order to topic {} succeeded: {}", props.getOrderPaymentResultTopicName(), result.getRecordMetadata());
                    }
                });
    }
}

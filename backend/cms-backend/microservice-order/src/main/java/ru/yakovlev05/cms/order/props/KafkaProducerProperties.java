package ru.yakovlev05.cms.order.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProducerProperties {
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private String producerKeySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String producerValueSerializer;

    private String orderValidationInputTopicName;
    private int orderValidationInputTopicPartitions;
    private int orderValidationInputTopicReplicas;

    private String orderPaymentCreateTopicName;
    private int orderPaymentCreateTopicPartitions;
    private int orderPaymentCreateTopicReplicas;
}

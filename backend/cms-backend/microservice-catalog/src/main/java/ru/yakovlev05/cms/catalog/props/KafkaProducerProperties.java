package ru.yakovlev05.cms.catalog.props;

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

    private String productTopicName;

    private int productTopicPartitions;

    private int productTopicReplicas;

    private String orderValidationResultTopicName;

    private int orderValidationResultTopicPartitions;

    private int orderValidationResultTopicReplicas;
}

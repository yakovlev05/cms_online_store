package ru.yakovlev05.cms.order.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProducerProperties {
    private String bootstrapServers;
    private String producerKeySerializer;
    private String producerValueSerializer;
    private String orderValidationInputTopicName;
    private int orderValidationInputTopicPartitions;
    private int orderValidationInputTopicReplicas;
}

package ru.yakovlev05.cms.catalog.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class KafkaConsumerProperties {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String producerKeyDeserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String producerValueDeserializer;

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackages;

    @Value("${spring.kafka.order-validation-input-topic-name}")
    private String orderValidationInputTopicName;
}

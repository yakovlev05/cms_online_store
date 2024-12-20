package ru.yakovlev05.cms.order.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import ru.yakovlev05.cms.order.props.KafkaProducerProperties;

@RequiredArgsConstructor
@Configuration
public class KafkaTopicConfig {

    private final KafkaProducerProperties props;

    @Bean
    public NewTopic orderValidationTopic() {
        return TopicBuilder.name(props.getOrderValidationInputTopicName())
                .partitions(props.getOrderValidationInputTopicPartitions())
                .replicas(props.getOrderValidationInputTopicReplicas())
                .build();
    }

}

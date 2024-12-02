package ru.yakovlev05.cms.catalog.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import ru.yakovlev05.cms.catalog.props.KafkaProducerProperties;

@RequiredArgsConstructor
@Configuration
public class KafkaTopicConfig {

    private final KafkaProducerProperties props;

    @Bean
    public NewTopic productTopic() {
        return TopicBuilder.name(props.getProductTopicName())
                .partitions(props.getProductTopicPartitions())
                .replicas(props.getProductTopicReplicas())
                .build();
    }
}

package ru.yakovlev05.cms.auth.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import ru.yakovlev05.cms.auth.props.KafkaProducerProperties;

@RequiredArgsConstructor
@Configuration
public class KafkaTopicConfig {

    private final KafkaProducerProperties kafkaProducerProperties;

    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name(kafkaProducerProperties.getUserTopicName())
                .partitions(kafkaProducerProperties.getUserTopicPartitions())
                .replicas(kafkaProducerProperties.getUserTopicReplicas())
                .build();
    }
}

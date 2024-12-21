package ru.yakovlev05.cms.payment.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import ru.yakovlev05.cms.payment.props.KafkaProducerProperties;

@RequiredArgsConstructor
@Configuration
public class KafkaTopicConfig {

    private final KafkaProducerProperties props;

    @Bean
    public NewTopic orderPaymentResultTopic() {
        return TopicBuilder.name(props.getOrderPaymentResultTopicName())
                .partitions(props.getOrderPaymentResultTopicPartitions())
                .replicas(props.getOrderPaymentResultTopicReplicas())
                .build();
    }
}

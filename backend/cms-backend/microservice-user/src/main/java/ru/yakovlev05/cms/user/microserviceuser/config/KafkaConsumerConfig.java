//package ru.yakovlev05.cms.user.microserviceuser.config;
//
//import lombok.RequiredArgsConstructor;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import ru.yakovlev05.cms.user.microserviceuser.props.KafkaConsumerProperties;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RequiredArgsConstructor
//@Configurable
//public class KafkaConsumerConfig {
//
//    private final KafkaConsumerProperties kafkaConsumerProperties;
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerProperties.getBootstrapServers());
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,kafkaConsumerProperties.getProducerKeyDeserializer());
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,kafkaConsumerProperties.getProducerValueDeserializer());
//        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerProperties.getConsumerGroupId());
//    }
//}

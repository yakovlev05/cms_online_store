package ru.yakovlev05.cms.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.dto.UserDto;
import ru.yakovlev05.cms.auth.props.KafkaProducerProperties;
import ru.yakovlev05.cms.auth.service.KafkaService;
import ru.yakovlev05.cms.core.entity.OtpChannelType;
import ru.yakovlev05.cms.core.event.EventType;
import ru.yakovlev05.cms.core.event.NotificationEvent;
import ru.yakovlev05.cms.core.event.UserEvent;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaProducerProperties props;

    @Override
    public void sendUserCreatedEvent(String userId, UserDto userDto) {
        UserEvent data = UserEvent.builder()
                .eventType(EventType.CREATE)
                .isProduceByUserService(false)
                .id(userId)
                .firstName(null)
                .lastName(null)
                .patronymic(null)
                .phoneNumber(userDto.getPhoneNumber())
                .build();

        log.info("Send data to topic auth.user.created: {}", data);
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate
                .send(props.getUserTopicName(), String.valueOf(userId), data);

        future
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Send data to topic {} failed", props.getUserTopicName(), exception);
                    } else {
                        log.info("Send data to topic {} success: {}", props.getUserTopicName(), result.getRecordMetadata());
                    }
                });
    }

    @Override
    public void sendNotificationEvent(String message, String destination, OtpChannelType channelType) {
        NotificationEvent event = NotificationEvent.builder()
                .channelType(channelType)
                .destination(destination)
                .text(message)
                .build();

        log.info("Send event to topic {}: {}", props.getNotificationTopicName(), event);

        kafkaTemplate.send(props.getNotificationTopicName(), event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Send event to topic {} failed", props.getNotificationTopicName(), exception);
                    } else {
                        log.info("Send event to topic {} success: {}", props.getNotificationTopicName(), result.getRecordMetadata());
                    }
                });

    }
}

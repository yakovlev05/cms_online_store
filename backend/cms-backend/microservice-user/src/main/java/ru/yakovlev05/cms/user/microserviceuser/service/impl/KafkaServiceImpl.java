package ru.yakovlev05.cms.user.microserviceuser.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.core.event.EventType;
import ru.yakovlev05.cms.core.event.UserEvent;
import ru.yakovlev05.cms.user.microserviceuser.dto.RequestUserDto;
import ru.yakovlev05.cms.user.microserviceuser.props.KafkaProducerProperties;
import ru.yakovlev05.cms.user.microserviceuser.service.KafkaService;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaProducerProperties props;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void sendUserCreatedEvent(String userId, RequestUserDto dto) {
        UserEvent userEvent = UserEvent.builder()
                .eventType(EventType.CREATE)
                .isRegisteredByClient(false)
                .id(userId)
                .firstName(dto.getFistName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .phoneNumber(dto.getPhoneNumber())
                .encodedPassword(passwordEncoder.encode(dto.getPassword()))
                .build();

        log.info("Send data to topic {}: {}", props.getUserTopicName(), userEvent);

        kafkaTemplate.send(props.getUserTopicName(), userId, userEvent)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Send data to topic {} failed", props.getUserTopicName(), exception);
                    } else {
                        log.info("Send data to topic {} success: {}", props.getUserTopicName(), result.getRecordMetadata());
                    }
                });
    }
}

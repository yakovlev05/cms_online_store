package ru.yakovlev05.cms.user.microserviceuser.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.core.event.EventType;
import ru.yakovlev05.cms.core.event.UserEvent;
import ru.yakovlev05.cms.user.microserviceuser.entity.User;
import ru.yakovlev05.cms.user.microserviceuser.props.KafkaProducerProperties;
import ru.yakovlev05.cms.user.microserviceuser.service.KafkaService;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaProducerProperties props;

    private final PasswordEncoder passwordEncoder;

    /**
     * @param user сущность пользователя
     * @param password пароль (может быть пустым полем, если это запрос на удаление / изменение, например)
     * @param eventType тип события
     */
    @Override
    public void sendUserEvent(User user, String password, EventType eventType) {
        UserEvent userEvent = UserEvent.builder()
                .eventType(eventType)
                .isProduceByUserService(true)
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .patronymic(user.getPatronymic())
                .phoneNumber(user.getPhoneNumber())
                .build();

        if (password != null) {
            userEvent.setEncodedPassword(passwordEncoder.encode(password));
        }

        log.info("Send data to topic {}: {}", props.getUserTopicName(), userEvent);

        kafkaTemplate.send(props.getUserTopicName(), user.getId(), userEvent)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Send data to topic {} failed", props.getUserTopicName(), exception);
                    } else {
                        log.info("Send data to topic {} success: {}", props.getUserTopicName(), result.getRecordMetadata());
                    }
                });
    }
}

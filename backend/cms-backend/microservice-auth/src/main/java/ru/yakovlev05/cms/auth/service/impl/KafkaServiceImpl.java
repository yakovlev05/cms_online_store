package ru.yakovlev05.cms.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.dto.UserDto;
import ru.yakovlev05.cms.auth.service.KafkaService;
import ru.yakovlev05.cms.core.entity.UserRole;
import ru.yakovlev05.cms.core.event.UserCreatedEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    @Override
    public void sendUserCreatedEvent(long userId, UserDto userDto, Set<UserRole> roles) {
        UserCreatedEvent data = UserCreatedEvent.builder()
                .id(userId)
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .patronymic(userDto.patronymic())
                .phoneNumber(userDto.phoneNumber())
                .roles(roles)
                .build();

        log.info("Send data to topic auth.user.created: {}", data);
        CompletableFuture<SendResult<String, UserCreatedEvent>> future = kafkaTemplate
                .send("auth.user.created", String.valueOf(userId), data);

        future
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Send data to topic auth.user.created failed", exception);
                    } else {
                        log.info("Send data to topic auth.user.created success: {}", result.getRecordMetadata());
                    }
                });
    }

}

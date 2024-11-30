package ru.yakovlev05.cms.user.microserviceuser.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.core.event.UserCreatedEvent;
import ru.yakovlev05.cms.user.microserviceuser.entity.User;
import ru.yakovlev05.cms.user.microserviceuser.service.UserService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "auth.user.created")
public class UserCreatedEventListener {

    private final UserService userService;

    @KafkaHandler
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        log.info("Received user created event: {}", event);

        User user = User.builder()
                .id(event.getId())
                .phoneNumber(event.getPhoneNumber())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .patronymic(event.getPatronymic())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userService.create(user);

        log.info("Created user on event: {}", event);
    }
}

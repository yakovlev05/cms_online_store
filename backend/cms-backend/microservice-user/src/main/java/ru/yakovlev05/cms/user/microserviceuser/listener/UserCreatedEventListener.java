package ru.yakovlev05.cms.user.microserviceuser.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.core.event.EventType;
import ru.yakovlev05.cms.core.event.UserEvent;
import ru.yakovlev05.cms.user.microserviceuser.entity.User;
import ru.yakovlev05.cms.user.microserviceuser.service.UserService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "user")
public class UserCreatedEventListener {

    private final UserService userService;

    @KafkaHandler
    public void handleUserEvent(UserEvent event) {
        // обработка только новых (созданных) объектов
        if (!event.getEventType().equals(EventType.CREATE)) {
            return;
        }
        log.info("Received user event: {}", event);

        User user = User.builder()
                .id(event.getId())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .patronymic(event.getPatronymic())
                .phoneNumber(event.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userService.create(user);

        log.info("Created user on event: {}", event);
    }
}

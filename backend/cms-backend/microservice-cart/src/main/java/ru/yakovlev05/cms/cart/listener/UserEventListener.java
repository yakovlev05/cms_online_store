package ru.yakovlev05.cms.cart.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.cart.entity.User;
import ru.yakovlev05.cms.cart.service.UserService;
import ru.yakovlev05.cms.core.event.EventType;
import ru.yakovlev05.cms.core.event.UserEvent;

import java.util.Objects;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "user")
public class UserEventListener {

    private final UserService userService;

    @KafkaHandler
    public void handleUserEvent(UserEvent event) {
        if (Objects.requireNonNull(event.getEventType()) == EventType.CREATE) {
            handleUserCreatedEvent(event);
        }
    }

    private void handleUserCreatedEvent(UserEvent event) {
        log.info("Received user event {}", event);

        User user = User.builder()
                .id(event.getId())
                .build();

        userService.create(user);

        log.info("User created");
    }
}

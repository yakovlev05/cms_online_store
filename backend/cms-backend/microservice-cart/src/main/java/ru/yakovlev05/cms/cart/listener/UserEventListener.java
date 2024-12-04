package ru.yakovlev05.cms.cart.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.cart.entity.User;
import ru.yakovlev05.cms.cart.service.UserService;
import ru.yakovlev05.cms.core.event.UserEvent;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "user")
public class UserEventListener {

    private final UserService userService;

    @KafkaHandler
    public void handleUserEvent(UserEvent event) {
        log.info("Received user event: {}", event);
        switch (event.getEventType()) {
            case CREATE:
                handleUserCreatedEvent(event);
                break;
            case DELETE:
                handleUserDeletedEvent(event);
                break;
            default:
                break;
        }
    }

    private void handleUserCreatedEvent(UserEvent event) {
        log.info("Handle created event");

        User user = User.builder()
                .id(event.getId())
                .build();

        userService.create(user);

        log.info("User created");
    }

    private void handleUserDeletedEvent(UserEvent event) {
        log.info("Handle deleted event");

        userService.deleteById(event.getId());

        log.info("User deleted");
    }
}

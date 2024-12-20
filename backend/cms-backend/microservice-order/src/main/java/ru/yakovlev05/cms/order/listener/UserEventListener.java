package ru.yakovlev05.cms.order.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.core.event.UserEvent;
import ru.yakovlev05.cms.order.entity.User;
import ru.yakovlev05.cms.order.service.UserService;

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
            case CREATE, UPDATE:
                handleUserCreatedOrUpdatedEvent(event);
                break;
            case DELETE:
                handleUserDeletedEvent(event);
                break;
            default:
                break;
        }
    }

    private void handleUserCreatedOrUpdatedEvent(UserEvent event) {
        log.info("Handle {} event", event.getEventType());

        User user = User.builder()
                .id(event.getId())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .patronymic(event.getPatronymic())
                .phoneNumber(event.getPhoneNumber())
                .build();

        userService.save(user);

        log.info("User saved, id: {}", user.getId());
    }

    private void handleUserDeletedEvent(UserEvent event) {
        log.info("Handle deleted event");

        userService.deleteById(event.getId());

        log.info("User deleted");
    }
}
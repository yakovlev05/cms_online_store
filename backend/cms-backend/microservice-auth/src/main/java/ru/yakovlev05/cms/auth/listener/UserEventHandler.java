package ru.yakovlev05.cms.auth.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.auth.service.UserService;
import ru.yakovlev05.cms.core.event.UserEvent;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "user")
public class UserEventHandler {

    private final UserService userService;

    @KafkaHandler
    public void handleUserEvent(UserEvent event) {
        if (!event.isProduceByUserService()) {
            return;
        }

        log.info("Received user event: {}", event);

        switch (event.getEventType()) {
            case CREATE:
                handleCreateEvent(event);
                break;
            case UPDATE:
                handleUpdateEvent(event);
                break;
            case DELETE:
                handleDeleteEvent(event);
                break;
            default:
                break;
        }

        log.info("Event successfully handled");
    }

    private void handleCreateEvent(UserEvent event) {
        log.info("Handle create type event}");

        User user = User.builder()
                .id(event.getId())
                .phoneNumber(event.getPhoneNumber())
                .password(event.getEncodedPassword())
                .isConfirmed(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userService.create(user);
    }

    private void handleUpdateEvent(UserEvent event) {
        log.info("Handle update type event");

        User user = userService.getById(event.getId());

        user.setPhoneNumber(event.getPhoneNumber());
        user.setUpdatedAt(LocalDateTime.now());

        if (event.getEncodedPassword() != null) {
            user.setPassword(event.getEncodedPassword());
        }

        userService.update(user);
    }

    private void handleDeleteEvent(UserEvent event) {
        log.info("Handle delete type event");

        User user = userService.getById(event.getId());
        userService.deleteById(user.getId());
    }
}

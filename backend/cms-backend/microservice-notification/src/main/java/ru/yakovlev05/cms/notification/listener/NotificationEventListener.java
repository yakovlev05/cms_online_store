package ru.yakovlev05.cms.notification.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.core.event.NotificationEvent;
import ru.yakovlev05.cms.notification.service.PhoneNotificationService;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "notification")
public class NotificationEventListener {

    private final PhoneNotificationService phoneNotificationService;

    @KafkaHandler
    public void handleNotificationEvent(NotificationEvent event) {
        switch (event.getChannelType()) {
            case FLASHCALL -> phoneNotificationService.sendFlashcall(event.getText(), event.getDestination());
            case VOICECODE -> phoneNotificationService.sendVoicecode(event.getText(), event.getDestination());
            case TEXT_TO_SPEECH -> phoneNotificationService.sendTextToSpeech(event.getText(), event.getDestination());
            default -> log.error("Unknown channel type: {}", event.getChannelType());
        }
    }

}

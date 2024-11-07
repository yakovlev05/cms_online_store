package ru.yakovlev05.cms.user.microserviceuser.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.core.event.UserCreatedEvent;

@Component
@Slf4j
@KafkaListener(topics = "auth.user.created")
public class UserCreatedEventListener {

    @KafkaHandler
    public void handle(UserCreatedEvent event){
        log.info("Received user created event: {}", event);
    }

}

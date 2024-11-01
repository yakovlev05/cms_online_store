package ru.yakovlev05.cms.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.auth.entity.UserRole;
import ru.yakovlev05.cms.auth.event.UserCreatedEvent;
import ru.yakovlev05.cms.auth.service.KafkaService;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    @Override
    public void sendUserCreatedEvent(User user, Set<UserRole> roles) {
        UserCreatedEvent data = UserCreatedEvent.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roles)
                .build();


        CompletableFuture<SendResult<String, UserCreatedEvent>> future = kafkaTemplate
                .send("auth.user.created", String.valueOf(user.getId()), data);
    }

}

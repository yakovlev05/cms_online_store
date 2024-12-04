package ru.yakovlev05.cms.user.microserviceuser.service;

import ru.yakovlev05.cms.core.event.EventType;
import ru.yakovlev05.cms.user.microserviceuser.entity.User;

public interface KafkaService {
    void sendUserEvent(User user, String password, EventType eventType);
}

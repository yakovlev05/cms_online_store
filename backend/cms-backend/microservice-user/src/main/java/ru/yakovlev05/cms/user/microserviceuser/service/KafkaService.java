package ru.yakovlev05.cms.user.microserviceuser.service;

import ru.yakovlev05.cms.user.microserviceuser.dto.RequestUserDto;

public interface KafkaService {
    void sendUserCreatedEvent(String userId, RequestUserDto dto);
}

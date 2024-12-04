package ru.yakovlev05.cms.auth.service;

import ru.yakovlev05.cms.auth.dto.UserDto;

public interface KafkaService {
    void sendUserCreatedEvent(String userId, UserDto userDto);
}

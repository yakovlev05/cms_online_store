package ru.yakovlev05.cms.auth.service;

import ru.yakovlev05.cms.auth.dto.UserDto;
import ru.yakovlev05.cms.core.entity.UserRole;

import java.util.Set;

public interface KafkaService {
    void sendUserCreatedEvent(long userId, UserDto userDto, Set<UserRole> roles);
}

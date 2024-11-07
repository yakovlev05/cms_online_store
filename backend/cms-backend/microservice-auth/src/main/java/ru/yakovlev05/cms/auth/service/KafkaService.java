package ru.yakovlev05.cms.auth.service;

import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.core.entity.UserRole;

import java.util.Set;

public interface KafkaService {
    void sendUserCreatedEvent(User user, Set<UserRole> roles);
}

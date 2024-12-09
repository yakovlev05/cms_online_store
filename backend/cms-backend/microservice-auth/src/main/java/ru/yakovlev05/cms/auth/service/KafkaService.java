package ru.yakovlev05.cms.auth.service;

import ru.yakovlev05.cms.auth.dto.UserDto;
import ru.yakovlev05.cms.core.entity.OtpChannelType;

public interface KafkaService {
    void sendUserCreatedEvent(String userId, UserDto userDto);

    void sendNotificationEvent(String message, String destination, OtpChannelType channelType);
}

package ru.yakovlev05.cms.core.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEvent {
    private EventType eventType;
    private boolean isProduceByUserService; // создано ли сервисом пользователей. true - да, false - сервис авторизации

    private String id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String encodedPassword;
}

package ru.yakovlev05.cms.core.event;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserEvent {
    private EventType eventType;

    private String id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
}

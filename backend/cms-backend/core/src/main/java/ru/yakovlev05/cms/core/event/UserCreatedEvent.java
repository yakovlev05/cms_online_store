package ru.yakovlev05.cms.core.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yakovlev05.cms.core.entity.UserRole;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserCreatedEvent {
    private long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private Set<UserRole> roles;
}
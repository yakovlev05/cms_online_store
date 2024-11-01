package ru.yakovlev05.cms.auth.event;

import lombok.Data;
import ru.yakovlev05.cms.auth.entity.UserRole;

import java.util.Set;

@Data
public class UserCreatedEvent {
    private long id;
    private String username;
    private String email;
    private String password;
    private Set<UserRole> roles;
}

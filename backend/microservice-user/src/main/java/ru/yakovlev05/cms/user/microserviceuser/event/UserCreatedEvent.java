package ru.yakovlev05.cms.user.microserviceuser.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yakovlev05.cms.user.microserviceuser.entity.UserRole;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserCreatedEvent {
    private long id;
    private String username;
    private String email;
    private Set<UserRole> roles;
}


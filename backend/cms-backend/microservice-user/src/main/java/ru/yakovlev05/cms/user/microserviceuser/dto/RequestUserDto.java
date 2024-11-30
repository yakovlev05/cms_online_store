package ru.yakovlev05.cms.user.microserviceuser.dto;

import lombok.Data;
import ru.yakovlev05.cms.core.entity.UserPermission;

import java.util.List;

@Data
public class RequestUserDto {
    private String fistName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String password;
    private List<UserPermission> permissions;
}

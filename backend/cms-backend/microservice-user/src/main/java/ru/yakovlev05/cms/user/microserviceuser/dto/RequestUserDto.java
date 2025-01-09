package ru.yakovlev05.cms.user.microserviceuser.dto;

import lombok.Data;

@Data
public class RequestUserDto {
    private String fistName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String password;
}

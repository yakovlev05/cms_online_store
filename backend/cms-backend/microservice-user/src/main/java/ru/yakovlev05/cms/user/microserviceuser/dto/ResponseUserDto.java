package ru.yakovlev05.cms.user.microserviceuser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseUserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String address;
}

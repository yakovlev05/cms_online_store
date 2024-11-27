package ru.yakovlev05.cms.auth.dto;

import lombok.Data;

@Data
public class UserDto {
    private String phoneNumber;
    private String password;
}

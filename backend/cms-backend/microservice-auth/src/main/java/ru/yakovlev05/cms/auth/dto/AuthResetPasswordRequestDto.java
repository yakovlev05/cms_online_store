package ru.yakovlev05.cms.auth.dto;

import lombok.Data;

@Data
public class AuthResetPasswordRequestDto {
    private String phoneNumber;
    private String newPassword;
    private String otpId;
}

package ru.yakovlev05.cms.auth.dto;

import lombok.Data;

@Data
public class AuthConfirmPhoneDto {
    private String phoneNumber;
    private String otpId;
}

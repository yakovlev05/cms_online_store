package ru.yakovlev05.cms.auth.dto;

import lombok.Data;

@Data
public class OtpConfirmRequest {
    private String id;
    private String code;
}

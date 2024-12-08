package ru.yakovlev05.cms.auth.dto;

import lombok.Data;

@Data
public class YandexCaptchaDto {
    private String status;
    private String message;
    private String host;
}

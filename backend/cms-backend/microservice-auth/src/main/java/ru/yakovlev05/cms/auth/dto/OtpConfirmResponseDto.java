package ru.yakovlev05.cms.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OtpConfirmResponseDto {
    private OtpConfirmStatus status;
}
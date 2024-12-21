package ru.yakovlev05.cms.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfirmationUrlDto {
    private String confirmationUrl;
}

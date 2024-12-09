package ru.yakovlev05.cms.auth.dto;

import lombok.Data;
import ru.yakovlev05.cms.core.entity.OtpChannelType;

@Data
public class OtpCreateSessionRequestDto {
    private String captchaToken;
    private OtpChannelType channelType;
    private String destination;
}

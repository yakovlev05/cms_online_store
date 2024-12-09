package ru.yakovlev05.cms.notification.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IDigitalFlashcallDto {
    private String channelType;
    private String senderName;
    private String destination;
    private String content;
}

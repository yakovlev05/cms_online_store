package ru.yakovlev05.cms.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IDigitalTextToSpeechDto {
    private String channelType;
    private String senderName;
    private String destination;
    private Content content;

    @AllArgsConstructor
    public static class Content {
        private String sex;
        private String contentType;
        private String text;
    }
}

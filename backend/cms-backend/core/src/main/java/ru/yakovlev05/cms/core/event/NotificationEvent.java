package ru.yakovlev05.cms.core.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yakovlev05.cms.core.entity.OtpChannelType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NotificationEvent {
    private OtpChannelType channelType;
    private String destination;
    private String text;
}

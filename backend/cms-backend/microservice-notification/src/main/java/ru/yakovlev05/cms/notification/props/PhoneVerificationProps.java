package ru.yakovlev05.cms.notification.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "notification")
public class PhoneVerificationProps {
    private String idigitalKey;
}

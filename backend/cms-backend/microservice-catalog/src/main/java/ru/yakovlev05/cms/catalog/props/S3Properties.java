package ru.yakovlev05.cms.catalog.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "s3")
public class S3Properties {
    private String bucket;
    private String accessKey;
    private String secretKey;
    private String url;
    private String customUrl;
}

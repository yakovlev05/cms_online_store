package ru.yakovlev05.cms.catalog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yakovlev05.cms.catalog.props.S3Properties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class S3Config {

    private final S3Properties s3Properties;

    @Bean
    public S3Client s3Client() {

        return S3Client.builder()
                .region(Region.of("minio"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                s3Properties.getAccessKey(),
                                s3Properties.getSecretKey())

                ))
                .endpointOverride(URI.create(s3Properties.getUrl()))
//                .serviceConfiguration(S3Configuration.builder()
//                        .pathStyleAccessEnabled(true) // Включить Path-Style
//                        .build())
                .build();
    }
}

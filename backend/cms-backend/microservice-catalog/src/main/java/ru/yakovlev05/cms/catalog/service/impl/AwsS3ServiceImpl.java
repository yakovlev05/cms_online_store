package ru.yakovlev05.cms.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yakovlev05.cms.catalog.props.S3Properties;
import ru.yakovlev05.cms.catalog.service.S3Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервис не доделан. Служит для работы с объектными хранилищами,
 * которые совместимы с апи Amazon
 * Minio не совместим с апи Amazon 💩, поэтому для него отдельная реализация
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class AwsS3ServiceImpl implements S3Service {

    private final S3Properties s3Properties;

    private final S3Client s3Client;

    private void ensureBucketExists(String bucketName) {
        try {
            s3Client.headBucket(request -> request.bucket(bucketName));
        } catch (NoSuchBucketException e) {
            log.info("Bucket does not exist: {}", bucketName);
            s3Client.createBucket(CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .acl(BucketCannedACL.PUBLIC_READ_WRITE) //access control list
                    .build());
            log.info("Bucket created: {}", bucketName);
        }
    }

    private void putFile(String bucketName, MultipartFile file) {
        try {
            Map<String, String> b = new HashMap<String, String>();
            b.put("bucket", bucketName);
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key("tttttt")
                            .metadata(b)
                            .acl(ObjectCannedACL.PUBLIC_READ)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void uploadPhoto(MultipartFile file) {
        ensureBucketExists(s3Properties.getBucket());
        putFile(s3Properties.getBucket(), file);
    }
}

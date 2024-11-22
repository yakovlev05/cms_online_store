package ru.yakovlev05.cms.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yakovlev05.cms.catalog.exception.S3Exception;
import ru.yakovlev05.cms.catalog.props.S3Properties;
import ru.yakovlev05.cms.catalog.service.S3Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class S3ServiceImpl implements S3Service {

    private final S3Properties s3Properties;

    private final S3Client s3Client;

    private void ensureBucketExists(String bucketName) {
        try {
            s3Client.headBucket(HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build());
        } catch (NoSuchBucketException e) {
            log.info("Bucket does not exist: {}", bucketName);
            throw new S3Exception("Bucket " + bucketName + " does not exist");
        }
    }

    private void putFile(String bucketName, MultipartFile file) {
        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(UUID.randomUUID().toString())
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new S3Exception("Failed get bytes from file");
        } catch (Exception e) {
            throw new S3Exception("Failed to put file " + file.getOriginalFilename() + " to bucket " + bucketName);
        }
    }


    @Override
    public void uploadPhoto(MultipartFile file) {
        ensureBucketExists(s3Properties.getBucket());
        putFile(s3Properties.getBucket(), file);
    }
}

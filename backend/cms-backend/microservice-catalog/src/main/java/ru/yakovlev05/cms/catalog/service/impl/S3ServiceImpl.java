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
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

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

    private void putFile(String bucketName, MultipartFile file, String key) {
        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new S3Exception("Failed get bytes from file");
        } catch (Exception e) {
            throw new S3Exception("Failed to put file " + file.getOriginalFilename() + " to bucket " + bucketName);
        }
        getUrl(key);
    }

    private void deleteFile(String bucketName, String key) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());
    }


    @Override
    public void putImage(MultipartFile file, String fileName) {
        String mime = file.getContentType();
        if (mime != null && !mime.startsWith("image/")) {
            throw new S3Exception("Invalid image type: " + mime);
        }

        ensureBucketExists(s3Properties.getBucket());
        putFile(s3Properties.getBucket(), file, fileName);
    }

    @Override
    public void deleteImage(String fileName) {
        ensureBucketExists(s3Properties.getBucket());
        deleteFile(s3Properties.getBucket(), fileName);
    }

    @Override
    public String getUrl(String fileName) {
        try {
            return s3Client.utilities().getUrl(GetUrlRequest.builder()
                    .bucket(s3Properties.getBucket())
                    .key(fileName)
                    .build()).toURI().toString();
        } catch (Exception e) {
            throw new S3Exception("Failed to get url for file " + fileName);
        }
    }

}

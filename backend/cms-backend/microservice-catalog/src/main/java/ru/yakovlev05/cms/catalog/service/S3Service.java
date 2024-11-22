package ru.yakovlev05.cms.catalog.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    void putImage(MultipartFile file, String fileName);

    void deleteImage(String fileName);

    String getUrl(String fileName);
}

package ru.yakovlev05.cms.catalog.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    void uploadPhoto(MultipartFile file);
}

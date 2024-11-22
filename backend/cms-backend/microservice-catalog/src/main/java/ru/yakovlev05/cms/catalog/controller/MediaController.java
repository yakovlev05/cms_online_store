package ru.yakovlev05.cms.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.yakovlev05.cms.catalog.service.S3Service;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    private final S3Service s3Service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadPhoto(@RequestParam("file") MultipartFile file) {
        s3Service.uploadPhoto(file);
    }
}

package ru.yakovlev05.cms.catalog.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yakovlev05.cms.catalog.dto.MediaDto;

import java.util.List;

public interface MediaService {
    void uploadPhoto(MultipartFile file);

    void deletePhoto(String fileName);

    List<MediaDto> getMediaPage(int page, int limit);
}

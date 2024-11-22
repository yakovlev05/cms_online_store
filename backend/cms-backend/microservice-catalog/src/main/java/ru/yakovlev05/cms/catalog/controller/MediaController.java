package ru.yakovlev05.cms.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yakovlev05.cms.catalog.dto.MediaDto;
import ru.yakovlev05.cms.catalog.service.MediaService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadPhoto(@RequestParam("file") MultipartFile file) {
        mediaService.uploadPhoto(file);
    }

    @DeleteMapping("/{file-name}")
    public void deletePhoto(@PathVariable(name = "file-name") String fileName) {
        mediaService.deletePhoto(fileName);
    }

    @GetMapping
    public List<MediaDto> getPhotos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int limit) {
        return mediaService.getMediaPage(page, limit);
    }
}

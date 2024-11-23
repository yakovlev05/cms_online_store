package ru.yakovlev05.cms.catalog.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yakovlev05.cms.catalog.dto.MediaDto;
import ru.yakovlev05.cms.catalog.entity.Media;
import ru.yakovlev05.cms.catalog.entity.Product;
import ru.yakovlev05.cms.catalog.exception.BadRequestException;
import ru.yakovlev05.cms.catalog.repository.MediaRepository;
import ru.yakovlev05.cms.catalog.service.MediaService;
import ru.yakovlev05.cms.catalog.service.S3Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MediaServiceImpl implements MediaService {

    private final S3Service s3Service;

    private final MediaRepository mediaRepository;

    private Media getMediaByFileName(String fileName) {
        return mediaRepository.findByFileName(fileName)
                .orElseThrow(() ->
                        new BadRequestException("Not found media with file name " + fileName));
    }

    private MediaDto fillMediaDto(Media media) {
        return new MediaDto(
                media.getName(),
                s3Service.getUrl(media.getFileName()),
                media.getFileName()
        );
    }

    @Override
    public MediaDto uploadPhoto(MultipartFile file) {
        String generatedFileName = UUID.randomUUID().toString();
        Media media = Media.builder()
                .name(file.getOriginalFilename())
                .fileName(generatedFileName)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        s3Service.putImage(file, generatedFileName);
        mediaRepository.save(media);
        return fillMediaDto(media);
    }

    @Transactional
    @Override
    public void deletePhoto(String fileName) {
        Media media = getMediaByFileName(fileName);
        if (!media.getProducts().isEmpty() || media.getCollection() != null) {
            throw new BadRequestException("The media is used. You must delete relations with products and collections.");
        }
        mediaRepository.delete(media);
        s3Service.deleteImage(fileName);
    }

    @Override
    public List<MediaDto> getMediaPage(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return mediaRepository.findAll(pageable).getContent().stream()
                .map(this::fillMediaDto)
                .toList();
    }

    @Override
    public void assignPhotoToProduct(String fileName, Product product) {
        Media media = getMediaByFileName(fileName);
        media.getProducts().add(product);
        mediaRepository.save(media);
    }
}

package ru.yakovlev05.cms.catalog.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.dto.MediaDto;
import ru.yakovlev05.cms.catalog.dto.RequestCollectionDto;
import ru.yakovlev05.cms.catalog.dto.ResponseCategoryDto;
import ru.yakovlev05.cms.catalog.dto.ResponseCollectionDto;
import ru.yakovlev05.cms.catalog.entity.Collection;
import ru.yakovlev05.cms.catalog.exception.BadRequestException;
import ru.yakovlev05.cms.catalog.repository.CollectionRepository;
import ru.yakovlev05.cms.catalog.service.CategoryService;
import ru.yakovlev05.cms.catalog.service.CollectionService;
import ru.yakovlev05.cms.catalog.service.MediaService;
import ru.yakovlev05.cms.catalog.service.S3Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;

    private final CategoryService categoryService;
    private final MediaService mediaService;

    private final S3Service s3Service;

    private Collection getCollectionById(long id) {
        return collectionRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException("Collection with id " + id + " not found"));
    }

    private ResponseCollectionDto fillResponseCollectionDto(Collection collection) {
        return new ResponseCollectionDto(
                collection.getId(),
                new MediaDto(
                        collection.getPhoto().getName(),
                        s3Service.getCustomUrl(collection.getPhoto().getFileName()),
                        collection.getPhoto().getFileName()
                ),
                new ResponseCategoryDto(
                        collection.getCategory().getId(),
                        collection.getCategory().getName(),
                        collection.getCategory().getUrlName()
                )
        );
    }

    private void assignRelations(Collection collection, RequestCollectionDto collectionDto) {
        categoryService.assignCategoryToCollection(collectionDto.getCategoryUrlName(), collection);
        mediaService.assignPhotoToCollection(collectionDto.getPhotoFileName(), collection);
    }

    @Override
    public ResponseCollectionDto getCollection(long id) {
        Collection collection = getCollectionById(id);

        return fillResponseCollectionDto(collection);
    }

    @Transactional
    @Override
    public ResponseCollectionDto addCollection(RequestCollectionDto collectionDto) {
        Collection collection = Collection.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assignRelations(collection, collectionDto);

        collectionRepository.save(collection);

        return fillResponseCollectionDto(collection);
    }

    @Transactional
    @Override
    public ResponseCollectionDto updateCollection(long id, RequestCollectionDto collectionDto) {
        Collection collection = getCollectionById(id);
        collection.setUpdatedAt(LocalDateTime.now());

        assignRelations(collection, collectionDto);

        return fillResponseCollectionDto(collection);
    }

    @Override
    public void deleteCollection(long id) {
        Collection collection = getCollectionById(id);
        collectionRepository.delete(collection);
    }

    @Override
    public List<ResponseCollectionDto> getAllCollections(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Collection> collections = collectionRepository.findAll(pageable);

        return collections.getContent().stream()
                .map(this::fillResponseCollectionDto)
                .toList();
    }
}

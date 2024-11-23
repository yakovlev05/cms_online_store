package ru.yakovlev05.cms.catalog.service;

import ru.yakovlev05.cms.catalog.dto.RequestCollectionDto;
import ru.yakovlev05.cms.catalog.dto.ResponseCollectionDto;

import java.util.List;

public interface CollectionService {
    ResponseCollectionDto getCollection(long id);

    ResponseCollectionDto addCollection(RequestCollectionDto collectionDto);

    ResponseCollectionDto updateCollection(long id, RequestCollectionDto collectionDto);

    void deleteCollection(long id);

    List<ResponseCollectionDto> getAllCollections(int page, int limit);
}

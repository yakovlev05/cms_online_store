package ru.yakovlev05.cms.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.cms.catalog.dto.RequestCollectionDto;
import ru.yakovlev05.cms.catalog.dto.ResponseCollectionDto;
import ru.yakovlev05.cms.catalog.service.CollectionService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/collections")
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping("/{id}")
    public ResponseCollectionDto getCollection(@PathVariable long id) {
        return collectionService.getCollection(id);
    }

    @PostMapping
    public ResponseCollectionDto addCollection(@RequestBody RequestCollectionDto collectionDto) {
        return collectionService.addCollection(collectionDto);
    }

    @PutMapping("/{id}")
    public ResponseCollectionDto updateCollection(
            @PathVariable long id,
            @RequestBody RequestCollectionDto collectionDto
    ) {
        return collectionService.updateCollection(id, collectionDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCollection(@PathVariable long id) {
        collectionService.deleteCollection(id);
    }

    @GetMapping
    public List<ResponseCollectionDto> getAllCollections(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int limit) {
        return collectionService.getAllCollections(page, limit);
    }
}

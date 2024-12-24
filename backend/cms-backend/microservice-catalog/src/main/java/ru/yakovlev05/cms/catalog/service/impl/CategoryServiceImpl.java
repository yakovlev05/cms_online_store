package ru.yakovlev05.cms.catalog.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.dto.RequestCategoryDto;
import ru.yakovlev05.cms.catalog.dto.ResponseCategoryDto;
import ru.yakovlev05.cms.catalog.entity.Category;
import ru.yakovlev05.cms.catalog.entity.Collection;
import ru.yakovlev05.cms.catalog.entity.Product;
import ru.yakovlev05.cms.catalog.exception.BadRequestException;
import ru.yakovlev05.cms.catalog.repository.CategoryRepository;
import ru.yakovlev05.cms.catalog.service.CategoryService;
import ru.yakovlev05.cms.catalog.service.TransliterationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final TransliterationService transliterationService;

    private final Random random = new Random();

    private Category getCategoryByUrlName(String urlName) {
        return categoryRepository.findByUrlName(urlName)
                .orElseThrow(() -> new BadRequestException("Category with url name " + urlName + " not found"));
    }

    private String generateUrlName(String name) {
        String latinName = transliterationService.toLatin(name).toLowerCase();
        return latinName.replace(' ', '-') + "-" + random.nextInt(1000, 10000);
    }

    @Override
    public ResponseCategoryDto getCategory(String urlName) {
        Category category = getCategoryByUrlName(urlName);

        return new ResponseCategoryDto(category.getId(), category.getName(), category.getUrlName());
    }

    @Override
    public ResponseCategoryDto addCategory(RequestCategoryDto categoryDto) {
        Category category = Category.builder()
                .name(categoryDto.getName())
                .urlName(generateUrlName(categoryDto.getName()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        categoryRepository.save(category);

        return new ResponseCategoryDto(category.getId(), category.getName(), category.getUrlName());
    }

    @Override
    public ResponseCategoryDto updateCategory(String urlName, RequestCategoryDto categoryDto) {
        Category category = getCategoryByUrlName(urlName);
        category.setName(categoryDto.getName());
        category.setUrlName(generateUrlName(categoryDto.getName()));
        category.setUpdatedAt(LocalDateTime.now());

        categoryRepository.save(category);

        return new ResponseCategoryDto(category.getId(), category.getName(), category.getUrlName());
    }

    @Transactional
    @Override
    public void deleteCategory(String urlName) {
        Category category = getCategoryByUrlName(urlName);
        if (!category.getProducts().isEmpty() || !category.getCollections().isEmpty()) {
            throw new BadRequestException("The category is used. You must delete relations with category.");
        }

        categoryRepository.delete(category);
    }

    @Transactional
    @Override
    public void assignCategoryToProduct(String categoryUrlName, Product product) {
        Category category = getCategoryByUrlName(categoryUrlName);
        category.getProducts().add(product);
        product.getCategories().add(category);
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void assignCategoryToCollection(String categoryUrlName, Collection collection) {
        Category category = getCategoryByUrlName(categoryUrlName);
        category.getCollections().add(collection);
        collection.setCategory(category);
        categoryRepository.save(category);
    }

    @Override
    public List<ResponseCategoryDto> getCategoryList(int page, int limit, String directionSort, String keySort, String searchQuery) {
        Pageable pageable = PageRequest.of(
                page,
                limit,
                directionSort.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                keySort);

        Page<Category> categories;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            categories = categoryRepository.findByNameContainingIgnoreCase(searchQuery, pageable);
        } else {
            categories = categoryRepository.findAll(pageable);
        }

        return categories.stream()
                .map(category -> new ResponseCategoryDto(category.getId(), category.getName(), category.getUrlName()))
                .toList();
    }
}

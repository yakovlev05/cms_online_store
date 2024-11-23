package ru.yakovlev05.cms.catalog.service;

import ru.yakovlev05.cms.catalog.dto.RequestCategoryDto;
import ru.yakovlev05.cms.catalog.dto.ResponseCategoryDto;
import ru.yakovlev05.cms.catalog.entity.Collection;
import ru.yakovlev05.cms.catalog.entity.Product;

public interface CategoryService {
    ResponseCategoryDto getCategory(String urlName);

    ResponseCategoryDto addCategory(RequestCategoryDto categoryDto);

    ResponseCategoryDto updateCategory(String urlName, RequestCategoryDto categoryDto);

    void deleteCategory(String urlName);

    void assignCategoryToProduct(String categoryUrlName, Product product);

    void assignCategoryToCollection(String categoryUrlName, Collection collection);
}

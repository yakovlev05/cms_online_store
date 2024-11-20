package ru.yakovlev05.cms.catalog.service;

import ru.yakovlev05.cms.catalog.dto.RequestCategoryDto;
import ru.yakovlev05.cms.catalog.dto.ResponseCategoryDto;

public interface CategoryService {
    ResponseCategoryDto getCategory(String urlName);

    ResponseCategoryDto addCategory(RequestCategoryDto categoryDto);

    ResponseCategoryDto updateCategory(String urlName, RequestCategoryDto categoryDto);

    void deleteCategory(String urlName);
}

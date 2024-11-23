package ru.yakovlev05.cms.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.cms.catalog.dto.RequestCategoryDto;
import ru.yakovlev05.cms.catalog.dto.ResponseCategoryDto;
import ru.yakovlev05.cms.catalog.service.CategoryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{urlName}")
    public ResponseCategoryDto getCategory(@PathVariable String urlName) {
        return categoryService.getCategory(urlName);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PostMapping("/add")
    public ResponseCategoryDto addCategory(@RequestBody RequestCategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @PutMapping("/{urlName}")
    public ResponseCategoryDto updateCategory(@PathVariable String urlName, @RequestBody RequestCategoryDto categoryDto) {
        return categoryService.updateCategory(urlName, categoryDto);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @DeleteMapping("/{urlName}")
    public void deleteCategory(@PathVariable String urlName) {
        categoryService.deleteCategory(urlName);
    }
}

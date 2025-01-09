package ru.yakovlev05.cms.catalog.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.cms.catalog.dto.RequestCategoryDto;
import ru.yakovlev05.cms.catalog.dto.ResponseCategoryDto;
import ru.yakovlev05.cms.catalog.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{urlName}")
    public ResponseCategoryDto getCategory(@PathVariable String urlName) {
        return categoryService.getCategory(urlName);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER') || hasAuthority('PERMISSION_CATALOG')")
    @PostMapping("/add")
    public ResponseCategoryDto addCategory(@RequestBody RequestCategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER') || hasAuthority('PERMISSION_CATALOG')")
    @PutMapping("/{urlName}")
    public ResponseCategoryDto updateCategory(@PathVariable String urlName, @RequestBody RequestCategoryDto categoryDto) {
        return categoryService.updateCategory(urlName, categoryDto);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER') || hasAuthority('PERMISSION_CATALOG')")
    @DeleteMapping("/{urlName}")
    public void deleteCategory(@PathVariable String urlName) {
        categoryService.deleteCategory(urlName);
    }

    @GetMapping
    public List<ResponseCategoryDto> getListCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit,
            @Pattern(regexp = "asc|desc") @RequestParam(defaultValue = "desc") String directionSort,
            @Pattern(regexp = "createdAt") @RequestParam(defaultValue = "createdAt") String keySort,
            @RequestParam(required = false) String searchQuery) {
        return categoryService.getCategoryList(page, limit, directionSort, keySort, searchQuery);
    }
}

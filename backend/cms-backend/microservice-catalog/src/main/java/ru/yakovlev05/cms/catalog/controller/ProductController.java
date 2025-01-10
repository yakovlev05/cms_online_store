package ru.yakovlev05.cms.catalog.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.cms.catalog.dto.RequestProductDto;
import ru.yakovlev05.cms.catalog.dto.ResponseProductDto;
import ru.yakovlev05.cms.catalog.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{url-name}")
    public ResponseProductDto getProduct(@PathVariable(name = "url-name") String urlName) {
        return productService.getProduct(urlName);
    }

    @GetMapping()
    public List<ResponseProductDto> getProductsList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit,
            @Pattern(regexp = "asc|desc") @RequestParam(defaultValue = "desc") String directionSort,
            @Pattern(regexp = "price|createdAt|name")@RequestParam(defaultValue = "createdAt") String keySort,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String categoryUrlName) {
        return productService.getProductsList(page, limit, directionSort, keySort, searchQuery, categoryUrlName);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER') || hasAuthority('PERMISSION_CATALOG')")
    @PostMapping("/add")
    public ResponseProductDto addProduct(@RequestBody RequestProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER') || hasAuthority('PERMISSION_CATALOG')")
    @PutMapping("/{url-name}")
    public ResponseProductDto updateProduct(
            @PathVariable(name = "url-name") String urlName,
            @RequestBody RequestProductDto productDto
    ) {
        return productService.updateProduct(urlName, productDto);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER') || hasAuthority('PERMISSION_CATALOG')")
    @DeleteMapping("/{url-name}")
    public void deleteProduct(@PathVariable(name = "url-name") String urlName) {
        productService.deleteProduct(urlName);
    }
}

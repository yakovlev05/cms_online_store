package ru.yakovlev05.cms.catalog.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
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
            @Pattern(regexp = "price|createdAt")@RequestParam(defaultValue = "createdAt") String keySort,
            @RequestParam(required = false) String searchQuery) {
        return productService.getProductsList(page, limit, directionSort, keySort, searchQuery);
    }

    @PostMapping("/add")
    public ResponseProductDto addProduct(@RequestBody RequestProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @PutMapping("/{url-name}")
    public ResponseProductDto updateProduct(
            @PathVariable(name = "url-name") String urlName,
            @RequestBody RequestProductDto productDto
    ) {
        return productService.updateProduct(urlName, productDto);
    }

    @DeleteMapping("/{url-name}")
    public void deleteProduct(@PathVariable(name = "url-name") String urlName) {
        productService.deleteProduct(urlName);
    }
}

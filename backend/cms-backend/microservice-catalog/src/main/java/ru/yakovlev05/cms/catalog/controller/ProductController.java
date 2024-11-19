package ru.yakovlev05.cms.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.cms.catalog.dto.RequestProductDto;
import ru.yakovlev05.cms.catalog.dto.ResponseProductDto;
import ru.yakovlev05.cms.catalog.service.ProductService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{url_name}")
    public ResponseProductDto getProduct(@PathVariable(name = "url_name") String urlName) {
        return productService.getProduct(urlName);
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody RequestProductDto productDto) {
        productService.addProduct(productDto);
    }
}

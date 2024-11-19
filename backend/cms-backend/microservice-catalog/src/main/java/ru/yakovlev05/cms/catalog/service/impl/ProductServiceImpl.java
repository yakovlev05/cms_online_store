package ru.yakovlev05.cms.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.dto.ComponentDto;
import ru.yakovlev05.cms.catalog.dto.RequestProductDto;
import ru.yakovlev05.cms.catalog.dto.ResponseProductDto;
import ru.yakovlev05.cms.catalog.entity.Category;
import ru.yakovlev05.cms.catalog.entity.Media;
import ru.yakovlev05.cms.catalog.entity.Product;
import ru.yakovlev05.cms.catalog.exception.BadRequestException;
import ru.yakovlev05.cms.catalog.repository.ProductRepository;
import ru.yakovlev05.cms.catalog.service.ProductService;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    private Product getProductByUrlName(String urlName) {
        return productRepository.findByUrlName(urlName)
                .orElseThrow(() ->
                        new BadRequestException("Product with url_name " + urlName + " not found")
                );
    }

    @Override
    public ResponseProductDto getProduct(String urlName) {
        Product product = getProductByUrlName(urlName);

        return ResponseProductDto.builder()
                .name(product.getName())
                .urlName(product.getUrlName())
                .description(product.getDescription())
                .price(product.getPrice())
                .component(product.getComponent().stream()
                        .map(x ->
                                new ComponentDto(
                                        x.getName(),
                                        x.getCount())
                        ).toList()
                )
                .categoryName(product.getCategory().stream()
                        .map(Category::getName)
                        .toList()
                )
                .photoName(product.getMedia().stream()
                        .map(Media::getFileName)
                        .toList()
                )
                .mainPhotoName(product.getMainPhoto().getFileName())
                .build();
    }

    @Override
    public void addProduct(RequestProductDto productDto) {
        //todo: реализовать создание продукта
    }
}

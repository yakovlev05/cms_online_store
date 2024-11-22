package ru.yakovlev05.cms.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.dto.ComponentDto;
import ru.yakovlev05.cms.catalog.dto.RequestProductDto;
import ru.yakovlev05.cms.catalog.dto.ResponseCategoryDto;
import ru.yakovlev05.cms.catalog.dto.ResponseProductDto;
import ru.yakovlev05.cms.catalog.entity.Product;
import ru.yakovlev05.cms.catalog.exception.BadRequestException;
import ru.yakovlev05.cms.catalog.repository.ProductRepository;
import ru.yakovlev05.cms.catalog.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final S3Service s3Service;

    private final TransliterationServiceImpl transliterationService;

    private final ComponentService componentService;
    private final CategoryService categoryService;
    private final MediaService mediaService;

    private final Random random = new Random();


    private Product getProductByUrlName(String urlName) {
        return productRepository.findByUrlName(urlName)
                .orElseThrow(() ->
                        new BadRequestException("Product with url_name " + urlName + " not found")
                );
    }

    private String generateProductUrlName(String name) {
        String latinName = transliterationService.toLatin(name);
        return latinName.replace(' ', '-') + random.nextInt(1000, 10000);
    }

    @Override
    public ResponseProductDto getProduct(String urlName) {
        Product product = getProductByUrlName(urlName);

        List<ComponentDto> componentsDto = product.getComponent().stream()
                .map(x -> new ComponentDto(x.getName(), x.getCount()))
                .toList();

        List<ResponseCategoryDto> categoriesDto = product.getCategory().stream()
                .map(x -> new ResponseCategoryDto(x.getName(), x.getUrlName()))
                .toList();

        List<String> photoUrls = product.getMedia().stream()
                .map(x -> s3Service.getUrl(x.getFileName()))
                .toList();

        return ResponseProductDto.builder()
                .name(product.getName())
                .urlName(product.getUrlName())
                .description(product.getDescription())
                .price(product.getPrice())
                .mainPhotoUrl(s3Service.getUrl(product.getMainPhoto().getFileName()))
                .components(componentsDto)
                .categories(categoriesDto)
                .photoUrls(photoUrls)
                .build();
    }

    @Override
    public void addProduct(RequestProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .urlName(generateProductUrlName(productDto.getName()))
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        productDto.getComponentsNames()
                .forEach(x -> componentService.assignComponentToProduct(x, product));

        productDto.getCategoriesUrlsNames()
                .forEach(x -> categoryService.assignCategoryToProduct(x, product));

        productDto.getPhotosFileNames()
                .forEach(x -> mediaService.assignPhotoToProduct(x, product));

        mediaService.assignPhotoToProduct(productDto.getMainPhotoFileName(), product);

        productRepository.save(product);
    }
}

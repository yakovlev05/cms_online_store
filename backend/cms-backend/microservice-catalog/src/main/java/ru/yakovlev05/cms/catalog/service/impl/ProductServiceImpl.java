package ru.yakovlev05.cms.catalog.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.dto.ComponentDto;
import ru.yakovlev05.cms.catalog.dto.RequestProductDto;
import ru.yakovlev05.cms.catalog.dto.ResponseCategoryDto;
import ru.yakovlev05.cms.catalog.dto.ResponseProductDto;
import ru.yakovlev05.cms.catalog.entity.Component;
import ru.yakovlev05.cms.catalog.entity.Media;
import ru.yakovlev05.cms.catalog.entity.Product;
import ru.yakovlev05.cms.catalog.exception.BadRequestException;
import ru.yakovlev05.cms.catalog.repository.ProductRepository;
import ru.yakovlev05.cms.catalog.service.*;
import ru.yakovlev05.cms.core.event.EventType;

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

    private final KafkaService kafkaService;

    private final Random random = new Random();


    private Product getProductByUrlName(String urlName) {
        return productRepository.findByUrlName(urlName)
                .orElseThrow(() ->
                        new BadRequestException("Product with url_name " + urlName + " not found")
                );
    }

    private String generateProductUrlName(String name) {
        String latinName = transliterationService.toLatin(name).toLowerCase();
        return latinName.replace(' ', '-') + "-" + random.nextInt(1000, 10000);
    }

    private ResponseProductDto fillResponseProductDto(Product product) {
        List<ComponentDto> componentsDto = product.getComponents().stream()
                .map(x -> new ComponentDto(x.getName(), x.getCount(), x.getPrice(), x.isInStock()))
                .toList();

        List<ResponseCategoryDto> categoriesDto = product.getCategories().stream()
                .map(x -> new ResponseCategoryDto(x.getName(), x.getUrlName()))
                .toList();

        List<String> photoUrls = product.getMedia().stream()
                .map(x -> s3Service.getCustomUrl(x.getFileName()))
                .toList();

        return ResponseProductDto.builder()
                .name(product.getName())
                .urlName(product.getUrlName())
                .description(product.getDescription())
                .price(product.getPrice())
                .priceDiscount(product.getPriceDiscount())
                .mainPhotoUrl(s3Service.getCustomUrl(product.getMainPhoto().getFileName()))
                .components(componentsDto)
                .categories(categoriesDto)
                .photoUrls(photoUrls)
                .build();
    }

    private void assignRelatedEntitiesToProduct(RequestProductDto productDto, Product product) {
        productDto.getComponentsNames()
                .forEach(x -> componentService.assignComponentToProduct(x, product));

        productDto.getCategoriesUrlsNames()
                .forEach(x -> categoryService.assignCategoryToProduct(x, product));

        productDto.getPhotosFileNames()
                .forEach(x -> mediaService.assignPhotoToProduct(x, product));

        Media mainPhoto = mediaService.getMediaByFileName(productDto.getMainPhotoFileName());
        product.setMainPhoto(mainPhoto);
        productRepository.save(product);
    }

    private boolean getProductAvailable(String urlName) {
        Product product = getProductByUrlName(urlName);

        return product.getComponents().stream()
                .allMatch(Component::isInStock);
    }

    @Override
    public ResponseProductDto getProduct(String urlName) {
        Product product = getProductByUrlName(urlName);
        return fillResponseProductDto(product);
    }

    @Transactional
    @Override
    public ResponseProductDto addProduct(RequestProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .urlName(generateProductUrlName(productDto.getName()))
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .priceDiscount(productDto.getPriceDiscount())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assignRelatedEntitiesToProduct(productDto, product);

        productRepository.save(product);

        kafkaService.sendProductEvent(product, getProductAvailable(product.getUrlName()), EventType.CREATE);

        return fillResponseProductDto(product);
    }

    @Override
    public List<ResponseProductDto> getProductsList(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findAll(pageable).getContent().stream()
                .map(this::fillResponseProductDto)
                .toList();

    }

    @Transactional
    @Override
    public ResponseProductDto updateProduct(String urlName, RequestProductDto productDto) {
        Product product = getProductByUrlName(urlName);

        product.setName(productDto.getName());
        product.setUrlName(
                product.getName().equals(productDto.getName()) ?
                        product.getUrlName() :
                        generateProductUrlName(productDto.getName())
        );
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setPriceDiscount(productDto.getPriceDiscount());
        product.setUpdatedAt(LocalDateTime.now());

        assignRelatedEntitiesToProduct(productDto, product);

        productRepository.save(product);

        kafkaService.sendProductEvent(product, getProductAvailable(product.getUrlName()), EventType.UPDATE);

        return fillResponseProductDto(product);
    }

    @Override
    public void deleteProduct(String urlName) {
        Product product = getProductByUrlName(urlName);

        kafkaService.sendProductEvent(product, getProductAvailable(product.getUrlName()), EventType.DELETE);

        productRepository.delete(product);
    }
}

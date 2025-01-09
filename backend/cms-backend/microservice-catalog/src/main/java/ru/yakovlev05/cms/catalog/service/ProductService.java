package ru.yakovlev05.cms.catalog.service;

import ru.yakovlev05.cms.catalog.dto.RequestProductDto;
import ru.yakovlev05.cms.catalog.dto.ResponseProductDto;
import ru.yakovlev05.cms.catalog.entity.Product;

import java.util.List;

public interface ProductService {
    ResponseProductDto getProduct(String urlName);

    ResponseProductDto addProduct(RequestProductDto productDto);

    List<ResponseProductDto> getProductsList(int page, int limit, String directionSort, String keySort, String searchQuery, String categoryUrlName);

    ResponseProductDto updateProduct(String urlName, RequestProductDto productDto);

    void deleteProduct(String urlName);

    Product getProductById(long id);
}

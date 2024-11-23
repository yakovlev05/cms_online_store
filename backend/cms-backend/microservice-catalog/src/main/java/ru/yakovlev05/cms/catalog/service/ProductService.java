package ru.yakovlev05.cms.catalog.service;

import ru.yakovlev05.cms.catalog.dto.RequestProductDto;
import ru.yakovlev05.cms.catalog.dto.ResponseProductDto;

import java.util.List;

public interface ProductService {
    ResponseProductDto getProduct(String urlName);

    void addProduct(RequestProductDto productDto);

    List<ResponseProductDto> getProductsList(int page, int limit);

    ResponseProductDto updateProduct(String urlName, RequestProductDto productDto);

    void deleteProduct(String urlName);
}

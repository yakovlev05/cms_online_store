package ru.yakovlev05.cms.catalog.service;

import ru.yakovlev05.cms.catalog.dto.RequestProductDto;
import ru.yakovlev05.cms.catalog.dto.ResponseProductDto;

public interface ProductService {
    ResponseProductDto getProduct(String urlName);

    void addProduct(RequestProductDto productDto);
}

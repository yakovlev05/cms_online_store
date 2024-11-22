package ru.yakovlev05.cms.catalog.service;

import ru.yakovlev05.cms.catalog.dto.ComponentDto;
import ru.yakovlev05.cms.catalog.entity.Product;

import java.util.List;

public interface ComponentService {
    ComponentDto getComponent(String componentName);

    void addComponent(ComponentDto componentDto);

    ComponentDto updateComponent(String componentName, ComponentDto componentDto);

    void deleteComponent(String componentName);

    void assignComponentToProduct(String componentName, Product product);
}

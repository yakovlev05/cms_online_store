package ru.yakovlev05.cms.catalog.service;

import ru.yakovlev05.cms.catalog.dto.ComponentDto;
import ru.yakovlev05.cms.catalog.entity.Product;

public interface ComponentService {
    ComponentDto getComponent(String componentName);

    ComponentDto addComponent(ComponentDto componentDto);

    ComponentDto updateComponent(String componentName, ComponentDto componentDto);

    void deleteComponent(String componentName);

    void assignComponentToProduct(String componentName, Product product);
}

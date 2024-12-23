package ru.yakovlev05.cms.catalog.service;

import jakarta.validation.constraints.Pattern;
import ru.yakovlev05.cms.catalog.dto.ComponentRequestDto;
import ru.yakovlev05.cms.catalog.dto.ComponentResponseDto;
import ru.yakovlev05.cms.catalog.entity.Product;

import java.util.List;

public interface ComponentService {
    ComponentResponseDto getComponent(String componentName);

    ComponentResponseDto addComponent(ComponentRequestDto componentRequestDto);

    ComponentResponseDto updateComponent(String componentName, ComponentRequestDto componentRequestDto);

    void deleteComponent(String componentName);

    void assignComponentToProduct(String componentName, Product product);

    List<ComponentResponseDto> getListComponents(int page, int limit, String keySort, boolean isDescending, String searchQuery, boolean showOnlyInStock);
}

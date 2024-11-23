package ru.yakovlev05.cms.catalog.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.dto.ComponentDto;
import ru.yakovlev05.cms.catalog.entity.Component;
import ru.yakovlev05.cms.catalog.entity.Product;
import ru.yakovlev05.cms.catalog.exception.BadRequestException;
import ru.yakovlev05.cms.catalog.repository.ComponentRepository;
import ru.yakovlev05.cms.catalog.service.ComponentService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;

    private Component getComponentByName(String name) {
        return componentRepository.findByName(name)
                .orElseThrow(() ->
                        new BadRequestException("Not found component with name: " + name));
    }

    private boolean existsByName(String name) {
        return componentRepository.existsByName(name);
    }

    private void save(Component component) {
        if (existsByName(component.getName())) {
            throw new BadRequestException("Component with name " + component.getName() + " already exists");
        }
        componentRepository.save(component);
    }

    private ComponentDto fillComponentDto(Component component) {
        return new ComponentDto(
                component.getName(),
                component.getCount(),
                component.getPrice(),
                component.isInStock()
        );
    }

    @Override
    public ComponentDto getComponent(String componentName) {
        Component component = getComponentByName(componentName);

        return fillComponentDto(component);
    }

    @Override
    public ComponentDto addComponent(ComponentDto componentDto) {
        Component component = Component.builder()
                .name(componentDto.getName())
                .count(componentDto.getCount())
                .price(componentDto.getPrice())
                .isInStock(componentDto.isInStock())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        save(component);
        return fillComponentDto(component);
    }

    @Override
    public ComponentDto updateComponent(String componentName, ComponentDto componentDto) {
        Component component = getComponentByName(componentName);
        component.setName(componentDto.getName());
        component.setCount(componentDto.getCount());
        component.setPrice(componentDto.getPrice());
        component.setInStock(componentDto.isInStock());
        component.setUpdatedAt(LocalDateTime.now());
        componentRepository.save(component);

        return fillComponentDto(component);
    }

    @Transactional
    @Override
    public void deleteComponent(String componentName) {
        Component component = getComponentByName(componentName);
        if (!component.getProducts().isEmpty()) {
            throw new BadRequestException("The component is used. You must delete relations with products.");
        }

        componentRepository.delete(component);
    }

    @Transactional
    @Override
    public void assignComponentToProduct(String componentName, Product product) {
        Component component = getComponentByName(componentName);

        // Необходимо установить связь с двух сторон
        component.getProducts().add(product);
        product.getComponents().add(component);

        componentRepository.save(component);
    }
}

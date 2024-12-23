package ru.yakovlev05.cms.catalog.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.dto.ComponentRequestDto;
import ru.yakovlev05.cms.catalog.dto.ComponentResponseDto;
import ru.yakovlev05.cms.catalog.entity.Component;
import ru.yakovlev05.cms.catalog.entity.Product;
import ru.yakovlev05.cms.catalog.exception.BadRequestException;
import ru.yakovlev05.cms.catalog.repository.ComponentRepository;
import ru.yakovlev05.cms.catalog.service.ComponentService;

import java.time.LocalDateTime;
import java.util.List;

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

    private ComponentRequestDto fillComponentRequestDto(Component component) {
        return new ComponentRequestDto(
                component.getName(),
                component.getCount(),
                component.getPrice(),
                component.isInStock()
        );
    }

    private ComponentResponseDto fillComponentResponseDto(Component component) {
        return new ComponentResponseDto(
                component.getId(),
                component.getName(),
                component.getCount(),
                component.getPrice(),
                component.isInStock()
        );
    }

    @Override
    public ComponentResponseDto getComponent(String componentName) {
        Component component = getComponentByName(componentName);

        return fillComponentResponseDto(component);
    }

    @Override
    public ComponentResponseDto addComponent(ComponentRequestDto componentRequestDto) {
        Component component = Component.builder()
                .name(componentRequestDto.getName())
                .count(componentRequestDto.getCount())
                .price(componentRequestDto.getPrice())
                .isInStock(componentRequestDto.isInStock())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        save(component);
        return fillComponentResponseDto(component);
    }

    @Override
    public ComponentResponseDto updateComponent(String componentName, ComponentRequestDto componentRequestDto) {
        Component component = getComponentByName(componentName);
        component.setName(componentRequestDto.getName());
        component.setCount(componentRequestDto.getCount());
        component.setPrice(componentRequestDto.getPrice());
        component.setInStock(componentRequestDto.isInStock());
        component.setUpdatedAt(LocalDateTime.now());
        componentRepository.save(component);

        return fillComponentResponseDto(component);
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

    @Override
    public List<ComponentResponseDto> getListComponents(int page, int limit, String keySort, boolean isDescending, String searchQuery, boolean showOnlyInStock) {
        Pageable pageable = PageRequest.of(
                page,
                limit,
                isDescending ? Sort.Direction.DESC : Sort.Direction.ASC,
                keySort);

        Page<Component> components;
        if (searchQuery == null || searchQuery.isEmpty()) {

            if (showOnlyInStock) {
                components = componentRepository.findByIsInStock(true, pageable);
            } else {
                components = componentRepository.findAll(pageable);
            }

        } else {

            if (showOnlyInStock) {
                components = componentRepository.findByNameContainingIgnoreCaseAndIsInStock(searchQuery, true, pageable);
            } else {
                components = componentRepository.findByNameContainingIgnoreCase(searchQuery, pageable);
            }

        }

        return components.getContent().stream()
                .map(this::fillComponentResponseDto)
                .toList();
    }

}

package ru.yakovlev05.cms.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.dto.ComponentDto;
import ru.yakovlev05.cms.catalog.entity.Component;
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

    @Override
    public ComponentDto getComponent(String componentName) {
        Component component = getComponentByName(componentName);

        return new ComponentDto(component.getName(), component.getCount());
    }

    @Override
    public void addComponent(ComponentDto componentDto) {
        Component component = Component.builder()
                .name(componentDto.getName())
                .count(componentDto.getCount())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        save(component);
    }

    @Override
    public ComponentDto updateComponent(String componentName, ComponentDto componentDto) {
        Component component = getComponentByName(componentName);
        component.setName(componentDto.getName());
        component.setCount(componentDto.getCount());
        component.setUpdatedAt(LocalDateTime.now());
        componentRepository.save(component);

        return new ComponentDto(component.getName(), component.getCount());
    }

    @Override
    public void deleteComponent(String componentName) {
        Component component = getComponentByName(componentName);
        if (!component.getProducts().isEmpty()){
            throw new BadRequestException("The component is used");
        }
    }
}

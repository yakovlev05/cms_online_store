package ru.yakovlev05.cms.catalog.service;

import ru.yakovlev05.cms.catalog.dto.ComponentDto;

public interface ComponentService {
    ComponentDto getComponent(String componentName);

    void addComponent(ComponentDto componentDto);

    ComponentDto updateComponent(String componentName, ComponentDto componentDto);
}

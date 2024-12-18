package ru.yakovlev05.cms.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.cms.catalog.dto.ComponentDto;
import ru.yakovlev05.cms.catalog.service.ComponentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/component")
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/{componentName}")
    public ComponentDto getComponent(@PathVariable String componentName) {
        return componentService.getComponent(componentName);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PostMapping("/add")
    public ComponentDto addComponent(@RequestBody ComponentDto componentDto) {
        return componentService.addComponent(componentDto);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PutMapping("/{componentName}")
    public ComponentDto updateComponent(@PathVariable String componentName, @RequestBody ComponentDto componentDto) {
        return componentService.updateComponent(componentName, componentDto);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @DeleteMapping("/{componentName}")
    public void deleteComponent(@PathVariable String componentName) {
        componentService.deleteComponent(componentName);
    }
}

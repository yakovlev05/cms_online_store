package ru.yakovlev05.cms.catalog.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.cms.catalog.dto.ComponentRequestDto;
import ru.yakovlev05.cms.catalog.dto.ComponentResponseDto;
import ru.yakovlev05.cms.catalog.service.ComponentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/components")
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/{componentName}")
    public ComponentResponseDto getComponent(@PathVariable String componentName) {
        return componentService.getComponent(componentName);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PostMapping("/add")
    public ComponentResponseDto addComponent(@RequestBody ComponentRequestDto componentRequestDto) {
        return componentService.addComponent(componentRequestDto);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PutMapping("/{componentName}")
    public ComponentResponseDto updateComponent(@PathVariable String componentName, @RequestBody ComponentRequestDto componentRequestDto) {
        return componentService.updateComponent(componentName, componentRequestDto);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @DeleteMapping("/{componentName}")
    public void deleteComponent(@PathVariable String componentName) {
        componentService.deleteComponent(componentName);
    }

    @GetMapping
    public List<ComponentResponseDto> getListComponents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "true") boolean isDescending,
            @Pattern(regexp = "createdAt|price", message = "Not valid keySort") @RequestParam(defaultValue = "createdAt") String keySort,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(defaultValue = "false") boolean showOnlyInStock) {
        return componentService.getListComponents(page, limit, keySort, isDescending, searchQuery, showOnlyInStock);
    }
}

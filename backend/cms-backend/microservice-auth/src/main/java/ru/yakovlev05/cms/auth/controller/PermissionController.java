package ru.yakovlev05.cms.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakovlev05.cms.auth.dto.PermissionDto;
import ru.yakovlev05.cms.auth.service.PermissionService;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("/")
    public List<PermissionDto> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @GetMapping("/user/{user-id}")
    public List<PermissionDto> getUserPermissions(@PathVariable(name = "user-id") long userId) {
        return permissionService.getUserPermissions(userId);
    }
}

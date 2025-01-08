package ru.yakovlev05.cms.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    public List<PermissionDto> getUserPermissions(@PathVariable(name = "user-id") String userId) {
        return permissionService.getUserPermissions(userId);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PutMapping("/user/{user-id}")
    public List<PermissionDto> setPermissionsToUser(
            @PathVariable(name = "user-id") String userId,
            @RequestBody List<String> permissionNames) {
        return permissionService.setPermissionsToUser(userId, permissionNames);
    }
}

package ru.yakovlev05.cms.auth.service;

import ru.yakovlev05.cms.auth.dto.PermissionDto;

import java.util.List;

public interface PermissionService {
    List<PermissionDto> getAllPermissions();

    List<PermissionDto> getUserPermissions(String userId);

    List<PermissionDto> setPermissionsToUser(String userId, List<String> permissionNames);
}

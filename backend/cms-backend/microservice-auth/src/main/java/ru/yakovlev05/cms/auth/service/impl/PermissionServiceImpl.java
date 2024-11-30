package ru.yakovlev05.cms.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.dto.PermissionDto;
import ru.yakovlev05.cms.auth.entity.Permission;
import ru.yakovlev05.cms.auth.repository.PermissionRepository;
import ru.yakovlev05.cms.auth.service.PermissionService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public List<PermissionDto> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();

        return permissions.stream()
                .map(this::fillPermissionDto)
                .toList();
    }

    @Override
    public List<PermissionDto> getUserPermissions(long userId) {
        List<Permission> permissions = permissionRepository.findByUserId(userId);
        return permissions.stream()
                .map(this::fillPermissionDto)
                .toList();
    }


    private PermissionDto fillPermissionDto(Permission permission) {
        return new PermissionDto(permission.getName().name());
    }
}

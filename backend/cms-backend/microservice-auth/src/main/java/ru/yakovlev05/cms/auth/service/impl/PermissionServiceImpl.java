package ru.yakovlev05.cms.auth.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.dto.PermissionDto;
import ru.yakovlev05.cms.auth.entity.Permission;
import ru.yakovlev05.cms.auth.entity.Role;
import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.auth.exception.BadRequestException;
import ru.yakovlev05.cms.auth.repository.PermissionRepository;
import ru.yakovlev05.cms.auth.service.PermissionService;
import ru.yakovlev05.cms.auth.service.UserService;
import ru.yakovlev05.cms.core.entity.UserPermission;
import ru.yakovlev05.cms.core.entity.UserRole;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final UserService userService;

    @Override
    public List<PermissionDto> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();

        return permissions.stream()
                .map(this::fillPermissionDto)
                .toList();
    }

    @Override
    public List<PermissionDto> getUserPermissions(String userId) {
        List<Permission> permissions = permissionRepository.findByUserId(userId);
        return permissions.stream()
                .map(this::fillPermissionDto)
                .toList();
    }

    @Transactional
    @Override
    public List<PermissionDto> setPermissionsToUser(String userId, List<String> permissionNames) {
        User user = userService.getById(userId);

        if (user.getRoles().stream()
                .map(Role::getName).
                toList()
                .contains(UserRole.ROLE_OWNER)) {
            throw new BadRequestException("You can not change permissions of this user");
        }

        Set<Permission> permissions = permissionNames.stream()
                .map(this::getPermissionByName)
                .collect(Collectors.toSet());

        user.setPermissions(permissions);

        permissions.forEach(permission -> permission.getUsers().add(user));

        permissionRepository.saveAll(permissions);

        return permissions.stream()
                .map(this::fillPermissionDto)
                .toList();
    }


    private PermissionDto fillPermissionDto(Permission permission) {
        return new PermissionDto(permission.getName().name());
    }

    private Permission getPermissionByName(String name) {
        try {
            UserPermission permissionName = UserPermission.valueOf(name);

            return permissionRepository.findByName(permissionName)
                    .orElseThrow(() -> new BadRequestException("Permission " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Permission " + name + " does not exist");
        }
    }
}

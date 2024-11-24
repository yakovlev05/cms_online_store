package ru.yakovlev05.cms.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.entity.Role;
import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.auth.repository.RoleRepository;
import ru.yakovlev05.cms.auth.service.RoleService;
import ru.yakovlev05.cms.auth.service.UserService;
import ru.yakovlev05.cms.core.entity.UserRole;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final UserService userService;

    private final RoleRepository roleRepository;

    @Override
    public void assignRoleToUser(User user, UserRole userRole) {
        Role role = getByName(userRole);

        user.getRoles().add(role);
        userService.update(user);
    }

    private Role getByName(UserRole role) {
        return roleRepository.findByName(role)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

}

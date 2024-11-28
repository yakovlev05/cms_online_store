package ru.yakovlev05.cms.user.microserviceuser.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.core.entity.UserRole;
import ru.yakovlev05.cms.user.microserviceuser.repository.RoleRepository;
import ru.yakovlev05.cms.user.microserviceuser.service.RoleService;
import ru.yakovlev05.cms.user.microserviceuser.service.UserService;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final UserService userService;

    private final RoleRepository roleRepository;

    @Override
    public void assignRoleToUser(long id, UserRole userRole) {
        User user = userService.getById(id);
        Role role = getByName(userRole);

        user.getRoles().add(role);
        userService.update(user);
    }

    private Role getByName(UserRole role) {
        return roleRepository.findByName(role)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

}

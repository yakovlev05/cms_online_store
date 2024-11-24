package ru.yakovlev05.cms.auth.service;

import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.core.entity.UserRole;

public interface RoleService {
    void assignRoleToUser(User user, UserRole userRole);
}

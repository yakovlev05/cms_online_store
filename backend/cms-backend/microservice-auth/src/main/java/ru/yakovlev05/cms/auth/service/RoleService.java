package ru.yakovlev05.cms.auth.service;

import ru.yakovlev05.cms.auth.entity.UserRole;

public interface RoleService {
    void assignRoleToUser(long id, UserRole userRole);
}

package ru.yakovlev05.cms.user.microserviceuser.service;

import ru.yakovlev05.cms.core.entity.UserRole;

public interface RoleService {
    void assignRoleToUser(long id, UserRole userRole);
}

package ru.yakovlev05.cms.user.microserviceuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.core.entity.UserRole;
import ru.yakovlev05.cms.user.microserviceuser.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole role);
}

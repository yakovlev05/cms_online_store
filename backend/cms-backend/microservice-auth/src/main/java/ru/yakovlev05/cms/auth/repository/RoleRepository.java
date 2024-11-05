package ru.yakovlev05.cms.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.auth.entity.Role;
import ru.yakovlev05.cms.auth.entity.UserRole;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole role);
}

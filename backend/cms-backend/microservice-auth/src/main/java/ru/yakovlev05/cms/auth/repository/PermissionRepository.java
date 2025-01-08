package ru.yakovlev05.cms.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yakovlev05.cms.auth.entity.Permission;
import ru.yakovlev05.cms.core.entity.UserPermission;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("select u.permissions from User u where u.id = :userId")
    List<Permission> findByUserId(String userId);
    Optional<Permission> findByName(UserPermission name);
}

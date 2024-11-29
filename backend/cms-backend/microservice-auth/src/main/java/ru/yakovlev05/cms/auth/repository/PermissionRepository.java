package ru.yakovlev05.cms.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yakovlev05.cms.auth.entity.Permission;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("select u.permissions from User u where u.id = :userId")
    List<Permission> findByUserId(Long userId);
}

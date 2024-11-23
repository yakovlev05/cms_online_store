package ru.yakovlev05.cms.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.catalog.entity.Component;

import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component, Long> {
    Optional<Component> findByName(String name);
    boolean existsByName(String name);
}

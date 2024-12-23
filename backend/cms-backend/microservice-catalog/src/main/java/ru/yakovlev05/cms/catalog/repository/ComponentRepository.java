package ru.yakovlev05.cms.catalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yakovlev05.cms.catalog.entity.Component;

import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component, Long> {
    Optional<Component> findByName(String name);

    boolean existsByName(String name);

    Page<Component> findByNameContainingIgnoreCaseAndIsInStock(String name, boolean isInStock, Pageable pageable);

    Page<Component> findByIsInStock(boolean isInStock, Pageable pageable);

    Page<Component> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

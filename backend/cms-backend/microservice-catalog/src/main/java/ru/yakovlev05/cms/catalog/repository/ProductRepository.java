package ru.yakovlev05.cms.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.catalog.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByUrlName(String urlName);
}

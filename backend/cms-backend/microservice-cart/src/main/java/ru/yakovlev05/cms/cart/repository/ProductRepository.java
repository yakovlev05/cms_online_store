package ru.yakovlev05.cms.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.cart.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByUrlName(String urlName);
}

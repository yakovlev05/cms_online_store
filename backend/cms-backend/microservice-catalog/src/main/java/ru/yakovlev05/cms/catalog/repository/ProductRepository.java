package ru.yakovlev05.cms.catalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.catalog.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByUrlName(String urlName);

    boolean existsByUrlName(String urlName);

    //    @Query("SELECT p FROM product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByCategories_UrlName(String categoryUrlName, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndCategories_UrlName(String name, String categoryUrlName, Pageable pageable);
}

package ru.yakovlev05.cms.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.order.entity.Order;
import ru.yakovlev05.cms.order.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrder(Order order);
}

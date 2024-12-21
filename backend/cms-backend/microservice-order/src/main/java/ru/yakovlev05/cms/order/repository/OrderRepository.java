package ru.yakovlev05.cms.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
}

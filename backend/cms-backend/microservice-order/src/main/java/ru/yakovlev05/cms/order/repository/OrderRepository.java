package ru.yakovlev05.cms.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.order.entity.Order;
import ru.yakovlev05.cms.order.entity.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUser(User user);
}

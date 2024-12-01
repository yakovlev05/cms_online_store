package ru.yakovlev05.cms.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}

package ru.yakovlev05.cms.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.cart.entity.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByProduct_UrlNameAndUser_Id(String productUrlName, String userId);
}

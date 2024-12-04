package ru.yakovlev05.cms.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.cart.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
}

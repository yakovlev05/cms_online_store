package ru.yakovlev05.cms.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.cart.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

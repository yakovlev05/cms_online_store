package ru.yakovlev05.cms.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.order.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}

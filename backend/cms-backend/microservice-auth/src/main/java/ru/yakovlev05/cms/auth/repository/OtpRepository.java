package ru.yakovlev05.cms.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.auth.entity.Otp;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findById(String id);
}

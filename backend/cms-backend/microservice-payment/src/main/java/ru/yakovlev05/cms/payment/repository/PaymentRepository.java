package ru.yakovlev05.cms.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.payment.entity.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(String paymentId);
    Optional<Payment> findByOrderId(String orderId);
}

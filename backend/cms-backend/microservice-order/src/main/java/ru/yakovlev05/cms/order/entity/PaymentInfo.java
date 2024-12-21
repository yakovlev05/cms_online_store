package ru.yakovlev05.cms.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payment_info")
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "payment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "final_sum", nullable = false, scale = 2, precision = 10)
    private BigDecimal finalSum;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_url")
    private String paymentUrl;
}

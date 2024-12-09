package ru.yakovlev05.cms.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "otp")
public class Otp {

    @Id
    private String id;

    @Column(name = "code")
    private String code;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;

    @Column(name = "send_attempts_count", nullable = false)
    private int sendAttemptsCount;

    @Column(name = "confirm_attempts_count", nullable = false)
    private int confirmAttemptsCount;

    @Column(name = "is_confirmed", nullable = false)
    private boolean isConfirmed;

    /**
     * Устанавливается в true, когда с помощью этой сессии совершили подтверждение (регистрация, сброс пароля)
     */
    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;

    @ManyToOne
    @JoinColumn(name = "otp_info_id", nullable = false)
    private OtpInfo info;
}

package ru.yakovlev05.cms.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yakovlev05.cms.core.entity.OtpChannelType;

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

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type", nullable = false)
    private OtpChannelType channelType;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    @Column(name = "send_attempts_count", nullable = false)
    private int sendAttemptsCount;

    @Column(name = "confirm_attempts_count", nullable = false)
    private int confirmAttemptsCount;

    @Column(name = "is_confirmed", nullable = false)
    private boolean isConfirmed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

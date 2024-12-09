package ru.yakovlev05.cms.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yakovlev05.cms.core.entity.OtpChannelType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "otp_info")
public class OtpInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type", nullable = false)
    private OtpChannelType channelType;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @Column(name = "max_attempts_resend", nullable = false)
    private int maxAttemptsResend;

    @Column(name = "max_attempts_confirm", nullable = false)
    private int maxAttemptsConfirm;

    @Column(name = "interval_resend_in_seconds", nullable = false)
    private int intervalResendInSeconds;

    @Column(name = "time_to_confirm_in_seconds", nullable = false)
    private int timeToConfirmInSeconds;

    @OneToMany(mappedBy = "info")
    private List<Otp> otpList;
}

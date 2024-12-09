package ru.yakovlev05.cms.auth.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.dto.*;
import ru.yakovlev05.cms.auth.entity.Otp;
import ru.yakovlev05.cms.auth.entity.OtpInfo;
import ru.yakovlev05.cms.auth.exception.BadRequestException;
import ru.yakovlev05.cms.auth.repository.OtpRepository;
import ru.yakovlev05.cms.auth.service.CaptchaService;
import ru.yakovlev05.cms.auth.service.KafkaService;
import ru.yakovlev05.cms.auth.service.OtpInfoService;
import ru.yakovlev05.cms.auth.service.OtpService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;

    private final CaptchaService captchaService;
    private final OtpInfoService otpInfoService;
    private final KafkaService kafkaService;

    private final Random random = new Random();

    private static final String MESSAGE_TEMPLATE = "Ваш код: ";

    @Override
    public CreateSessionResponseDto createSession(OtpCreateSessionRequestDto request, HttpServletRequest httpServletRequest) {
        if (!captchaService.verify(request.getCaptchaToken(), httpServletRequest.getRemoteAddr())) {
            throw new BadRequestException("Captcha is not valid");
        }


        Otp otp = Otp.builder()
                .id(UUID.randomUUID().toString())
                .code(null)
                .destination(request.getDestination())
                .generatedAt(null)
                .sendAttemptsCount(0)
                .confirmAttemptsCount(0)
                .isConfirmed(false)
                .isExpired(false)
                .info(otpInfoService.getByChannelType(request.getChannelType()))
                .build();

        otpRepository.save(otp);

        return new CreateSessionResponseDto(otp.getId());
    }

    @Override
    public void send(OtpSendRequestDto request) {
        Otp otp = getById(request.getId());
        OtpInfo otpInfo = otp.getInfo();

        if (otp.isConfirmed()) {
            throw new BadRequestException("Otp already confirmed");
        }

        if (otp.getCode() != null) {

            if (otp.getSendAttemptsCount() >= otpInfo.getMaxAttemptsResend()) {
                throw new BadRequestException("The attempts are over");
            }

            Duration duration = Duration.between(otp.getGeneratedAt(), LocalDateTime.now());
            if (Math.abs(duration.getSeconds()) < otpInfo.getIntervalResendInSeconds()) {
                throw new BadRequestException("You can not resend code, wait "
                        + (otpInfo.getIntervalResendInSeconds() - Math.abs(duration.getSeconds()))
                        + " seconds");
            }

        }

        otp.setCode(generateCode());
        otp.setGeneratedAt(LocalDateTime.now());
        otp.setSendAttemptsCount(otp.getSendAttemptsCount() + 1);

        log.info("Send code {} to destination {}", otp.getCode(), otp.getDestination());
        kafkaService.sendNotificationEvent(
                getConfirmationMessage(otp.getCode()),
                otp.getDestination(),
                otpInfo.getChannelType());

        otpRepository.save(otp);
    }

    @Override
    public OtpConfirmResponseDto check(OtpConfirmRequest request) {
        Otp otp = getById(request.getId());
        OtpInfo otpInfo = otp.getInfo();

        if (otp.getCode() == null) {
            throw new BadRequestException("You must send code before");
        }

        if (otp.isConfirmed()) {
            throw new BadRequestException("Otp already confirmed");
        }

        Duration duration = Duration.between(otp.getGeneratedAt(), LocalDateTime.now());
        if (Math.abs(duration.getSeconds()) > otpInfo.getTimeToConfirmInSeconds()) {
            return new OtpConfirmResponseDto(OtpConfirmStatus.EXPIRED);
        }

        if (otp.getConfirmAttemptsCount() >= otpInfo.getMaxAttemptsConfirm()) {
            return new OtpConfirmResponseDto(OtpConfirmStatus.EXPIRED);
        }

        if (otp.getCode().equals(request.getCode())) {
            otp.setConfirmed(true);
            otp.setConfirmAttemptsCount(otp.getSendAttemptsCount() + 1);
            otpRepository.save(otp);
            return new OtpConfirmResponseDto(OtpConfirmStatus.OK);
        }

        otp.setConfirmAttemptsCount(otp.getConfirmAttemptsCount() + 1);
        otpRepository.save(otp);
        return new OtpConfirmResponseDto(OtpConfirmStatus.WRONG_CODE);
    }

    private String generateCode() {
        int code = 1_000 + random.nextInt(9_000);
        return String.valueOf(code);
    }

    private String getConfirmationMessage(String code) {
        String formattedCode = String.join("-", code.split(""));
        return MESSAGE_TEMPLATE + formattedCode;
    }

    @Override
    public Otp getById(String id) {
        return otpRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Otp not found: " + id));
    }

    @Override
    public void update(Otp otp) {
        otpRepository.save(otp);
    }
}

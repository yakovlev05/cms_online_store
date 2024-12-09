package ru.yakovlev05.cms.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.yakovlev05.cms.auth.dto.*;
import ru.yakovlev05.cms.auth.entity.Otp;

public interface OtpService {
    CreateSessionResponseDto createSession(OtpCreateSessionRequestDto request, HttpServletRequest httpServletRequest);

    void send(OtpSendRequestDto request);

    OtpConfirmResponseDto check(OtpConfirmRequest request);

    Otp getById(String id);

    void update(Otp otp);
}

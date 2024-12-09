package ru.yakovlev05.cms.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.yakovlev05.cms.auth.dto.*;

public interface OtpService {
    CreateSessionResponseDto createSession(OtpCreateSessionRequestDto request, HttpServletRequest httpServletRequest);

    void send(OtpSendRequestDto request);

    OtpConfirmResponseDto check(OtpConfirmRequest request);
}

package ru.yakovlev05.cms.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakovlev05.cms.auth.dto.*;
import ru.yakovlev05.cms.auth.service.OtpService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {

    private final OtpService otpService;

    @PostMapping
    public CreateSessionResponseDto createSession(
            @RequestBody OtpCreateSessionRequestDto request,
            HttpServletRequest httpServletRequest) {
        return otpService.createSession(request, httpServletRequest);
    }

    @PostMapping("/send")
    public void send(@RequestBody OtpSendRequestDto request){
        otpService.send(request);
    }

    @PostMapping("/check")
    public OtpConfirmResponseDto check(@RequestBody OtpConfirmRequest request){
        return otpService.check(request);
    }
}

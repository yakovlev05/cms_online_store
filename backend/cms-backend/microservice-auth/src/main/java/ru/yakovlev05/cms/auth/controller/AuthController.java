package ru.yakovlev05.cms.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.cms.auth.dto.*;
import ru.yakovlev05.cms.auth.service.AuthService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@RequestBody UserDto request) {
        return authService.registration(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDto request, @RequestParam(defaultValue = "true") boolean isClient) {
        return authService.login(request, isClient);
    }

    @PostMapping("/refresh")
    public JwtResponseDto refresh(@RequestBody JwtRefreshRequestDto request) {
        return authService.refresh(request);
    }

    @PostMapping("/confirm-phone")
    public void confirmPhone(@RequestBody AuthConfirmPhoneDto request) {
        authService.confirmPhone(request);
    }

    @PatchMapping("/reset-password")
    public void resetPassword(@RequestBody AuthResetPasswordRequestDto request) {
        authService.resetPassword(request);
    }

    @GetMapping("/check")
    public void checkAuth() {
    }
}

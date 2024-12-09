package ru.yakovlev05.cms.auth.service;

import org.springframework.http.ResponseEntity;
import ru.yakovlev05.cms.auth.dto.*;

public interface AuthService {
    ResponseEntity<Object> registration(UserDto request);

    ResponseEntity<Object> login(UserDto request);

    JwtResponseDto refresh(JwtRefreshRequestDto request);

    void confirmPhone(AuthConfirmPhoneDto request);

    void resetPassword(AuthResetPasswordRequestDto request);
}

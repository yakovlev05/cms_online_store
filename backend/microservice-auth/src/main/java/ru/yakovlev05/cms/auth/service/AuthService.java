package ru.yakovlev05.cms.auth.service;

import org.springframework.http.ResponseEntity;
import ru.yakovlev05.cms.auth.dto.JwtRefreshRequestDto;
import ru.yakovlev05.cms.auth.dto.JwtRequestDto;
import ru.yakovlev05.cms.auth.dto.JwtResponseDto;
import ru.yakovlev05.cms.auth.dto.UserDto;

public interface AuthService {
    ResponseEntity<String> registration(UserDto request);

    JwtResponseDto login(JwtRequestDto request);

    JwtResponseDto refresh(JwtRefreshRequestDto request);
}

package ru.yakovlev05.cms.auth.dto;

public record JwtResponseDto(String accessToken, String refreshToken) {
}

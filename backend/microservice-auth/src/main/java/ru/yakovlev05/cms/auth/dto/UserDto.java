package ru.yakovlev05.cms.auth.dto;

public record UserDto(
        String username,
        String email,
        String password
) {
}

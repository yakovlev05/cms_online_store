package ru.yakovlev05.cms.auth.dto;

public record UserDto(
        String firstName,
        String lastName,
        String patronymic,
        String phoneNumber,
        String password
) {
}

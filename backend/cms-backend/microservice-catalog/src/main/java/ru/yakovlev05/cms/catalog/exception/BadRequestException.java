package ru.yakovlev05.cms.catalog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BadRequestException extends RuntimeException {

    private final Map<String, String> errors;

    public BadRequestException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }

    public BadRequestException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
}

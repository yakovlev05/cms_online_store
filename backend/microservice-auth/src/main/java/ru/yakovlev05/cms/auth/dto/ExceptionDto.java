package ru.yakovlev05.cms.auth.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExceptionDto {
    public ExceptionDto(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.errors = new HashMap<>();
    }

    private String message;
    private final long timestamp;
    private Map<String, String> errors;
}

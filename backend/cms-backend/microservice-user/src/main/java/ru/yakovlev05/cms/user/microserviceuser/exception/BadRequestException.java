package ru.yakovlev05.cms.user.microserviceuser.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

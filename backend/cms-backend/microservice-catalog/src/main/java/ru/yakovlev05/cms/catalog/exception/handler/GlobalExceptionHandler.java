package ru.yakovlev05.cms.catalog.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yakovlev05.cms.catalog.dto.ExceptionDto;
import ru.yakovlev05.cms.catalog.exception.BadRequestException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleBadRequestException(BadRequestException e) {
        log.info("Handle BadRequestException: {}", e.getMessage());
        ExceptionDto exDto = new ExceptionDto(e.getMessage());
        exDto.setErrors(e.getErrors());
        return exDto;
    }
}

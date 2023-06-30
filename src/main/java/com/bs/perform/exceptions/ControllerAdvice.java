package com.bs.perform.exceptions;

import com.bs.perform.dtos.response.ErrorResultResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResultResponseDto illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] ex", e);
        return new ErrorResultResponseDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ErrorResultResponseDto nullPointerExceptionHandler(NullPointerException e) {
        log.error("[NullPointerException] ex", e);
        return new ErrorResultResponseDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResultResponseDto resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        log.error("[ResourceNotFoundException] ex", e);
        return new ErrorResultResponseDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomDatabaseException.class)
    public ErrorResultResponseDto customDatabaseExceptionHandler(CustomDatabaseException e) {
        log.error("[CustomDatabaseException] ex", e);
        return new ErrorResultResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResultResponseDto exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResultResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            e.getMessage());
    }
}

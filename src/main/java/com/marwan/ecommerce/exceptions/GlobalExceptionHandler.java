package com.marwan.ecommerce.exceptions;

import com.marwan.ecommerce.exceptions.abstractions.NotFoundException;
import com.marwan.ecommerce.exceptions.abstractions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleException(RuntimeException exception) {

        HttpStatus status = switch (exception) {
            case NotFoundException e -> HttpStatus.NOT_FOUND;
            case ValidationException e -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        ProblemDetail problemDetail = ProblemDetail.forStatus(status.value());
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setTitle(exception.getClass().getSimpleName());
        return problemDetail;
    }
}

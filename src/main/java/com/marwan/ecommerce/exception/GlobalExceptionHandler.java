package com.marwan.ecommerce.exception;

import com.marwan.ecommerce.exception.abstractions.CustomException;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;
import com.marwan.ecommerce.exception.abstractions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(CustomException.class)
    public ProblemDetail handleCustomException(CustomException exception)
    {

        HttpStatus status = switch (exception) {
            case NotFoundException e -> HttpStatus.NOT_FOUND;
            case ValidationException e -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        ProblemDetail problemDetail = ProblemDetail.forStatus(status.value());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("code", exception.getCode());
        problemDetail.setProperties(properties);
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setTitle(exception.getClass().getSimpleName());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception)
    {
        ProblemDetail problemDetail = ProblemDetail.forStatus(500);
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setTitle(exception.getClass().getSimpleName());
        return problemDetail;
    }
}
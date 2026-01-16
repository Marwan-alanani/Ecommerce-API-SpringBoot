package com.marwan.ecommerce.exception.abstractions;

public abstract class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}

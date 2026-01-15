package com.marwan.ecommerce.exceptions.abstractions;

public abstract class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}

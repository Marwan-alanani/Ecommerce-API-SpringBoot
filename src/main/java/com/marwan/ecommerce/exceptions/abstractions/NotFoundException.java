package com.marwan.ecommerce.exceptions.abstractions;

public abstract class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}

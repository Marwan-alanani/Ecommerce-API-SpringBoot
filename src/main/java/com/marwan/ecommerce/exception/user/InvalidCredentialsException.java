package com.marwan.ecommerce.exception.user;

import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class InvalidCredentialsException extends ValidationException {
    public InvalidCredentialsException() {
        super(101,"Invalid email or password!");
    }
}

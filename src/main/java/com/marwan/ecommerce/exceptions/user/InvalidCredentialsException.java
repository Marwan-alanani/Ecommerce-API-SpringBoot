package com.marwan.ecommerce.exceptions.user;

import com.marwan.ecommerce.exceptions.abstractions.ValidationException;

public class InvalidCredentialsException extends ValidationException {
    public InvalidCredentialsException() {
        super("Invalid email or password!");
    }
}

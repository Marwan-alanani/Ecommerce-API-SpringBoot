package com.marwan.ecommerce.exception.user;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class InvalidCredentialsException extends ValidationException {
    public InvalidCredentialsException() {
        super(
                ExceptionCodes.InvalidCredentialsException,
                "Invalid email or password!");
    }
}

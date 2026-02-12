package com.marwan.ecommerce.exception.user;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class InvalidPasswordException extends ValidationException {
    public InvalidPasswordException() {
        super(ExceptionCodes.InvalidPasswordException, "Invalid password!");
    }
}

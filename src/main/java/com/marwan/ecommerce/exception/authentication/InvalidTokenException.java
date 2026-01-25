package com.marwan.ecommerce.exception.authentication;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class InvalidTokenException extends ValidationException
{
    public InvalidTokenException(String token)
    {
        super(ExceptionCodes.InvalidTokenException, "Given token: " + token + " is invalid!");
    }
}

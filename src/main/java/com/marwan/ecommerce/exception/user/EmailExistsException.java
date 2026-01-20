package com.marwan.ecommerce.exception.user;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class EmailExistsException extends ValidationException
{
    public EmailExistsException(String email)
    {
        super(
                ExceptionCodes.EmailExistsException,
                "Email " + email + " exists"
        );
    }
}

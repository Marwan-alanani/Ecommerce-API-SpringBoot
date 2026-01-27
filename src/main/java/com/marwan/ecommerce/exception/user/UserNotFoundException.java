package com.marwan.ecommerce.exception.user;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException
{
    public UserNotFoundException(UUID id)
    {
        super(
                ExceptionCodes.UserNotFoundException,
                "No user with the given id: " + id + " was found"
        );
    }
}

package com.marwan.ecommerce.exception.cart;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.ValidationException;

import java.util.UUID;

public class CartAlreadyExistsForUserException extends ValidationException
{
    public CartAlreadyExistsForUserException(UUID userId)
    {
        super(ExceptionCodes.CartAlreadyExistsForUserException,
                "User with id " + userId.toString() + " already has a cart!");
    }
}

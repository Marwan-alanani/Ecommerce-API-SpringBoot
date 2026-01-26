package com.marwan.ecommerce.exception.cart;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class CartWithUserIdNotFoundException extends NotFoundException
{
    public CartWithUserIdNotFoundException(UUID userId)
    {
        super(ExceptionCodes.CartWithUserIdNotFoundException,
                "Cart with given user id " + userId.toString() + " not found!");
    }
}

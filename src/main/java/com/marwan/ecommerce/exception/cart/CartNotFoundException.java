package com.marwan.ecommerce.exception.cart;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class CartNotFoundException extends NotFoundException
{
    public CartNotFoundException(UUID cartId)
    {
        super(
                ExceptionCodes.CartNotFoundException,
                "Cart with the given id: " + cartId.toString() + " not found!");
    }
}

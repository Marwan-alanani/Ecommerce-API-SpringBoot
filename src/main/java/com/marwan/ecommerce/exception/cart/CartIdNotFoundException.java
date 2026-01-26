package com.marwan.ecommerce.exception.cart;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class CartIdNotFoundException extends NotFoundException
{
    public CartIdNotFoundException(UUID cartId)
    {
        super(
                ExceptionCodes.CartIdNotFoundException,
                "Cart with the given id: " + cartId.toString() + " not found!");
    }
}

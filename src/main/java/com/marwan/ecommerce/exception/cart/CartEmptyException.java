package com.marwan.ecommerce.exception.cart;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.ValidationException;


public class CartEmptyException extends ValidationException
{
    public CartEmptyException()
    {
        super(ExceptionCodes.CartEmptyException, "User cart is empty!");
    }
}

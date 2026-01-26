package com.marwan.ecommerce.exception.product;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class NotEnoughProductException extends ValidationException
{
    public NotEnoughProductException(String productName, int productBalance, int requestQuantity)
    {
        super(ExceptionCodes.NotEnoughProductException,
                "Inventory has " + productBalance +
                        " of " + productName + " but " + requestQuantity + " was requested!");
    }
}

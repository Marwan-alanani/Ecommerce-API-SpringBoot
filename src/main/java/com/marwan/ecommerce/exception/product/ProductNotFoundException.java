package com.marwan.ecommerce.exception.product;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class ProductNotFoundException extends NotFoundException
{
    public ProductNotFoundException(UUID id)
    {
        super(ExceptionCodes.ProductNotFoundException, "Product with id: " + id + " not found!");
    }
}

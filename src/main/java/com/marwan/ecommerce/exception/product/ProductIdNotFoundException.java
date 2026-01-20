package com.marwan.ecommerce.exception.product;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class ProductIdNotFoundException extends NotFoundException
{
    public ProductIdNotFoundException(UUID id)
    {
        super(ExceptionCodes.ProductIdNotFoundException, "Product with id: " + id + " not found!");
    }
}

package com.marwan.ecommerce.exception.category;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class CategoryNotFoundException extends NotFoundException
{
    public CategoryNotFoundException(UUID id)
    {
        super(ExceptionCodes.CategoryNotFoundException, "Category with id " + id + " not found!");
    }
}

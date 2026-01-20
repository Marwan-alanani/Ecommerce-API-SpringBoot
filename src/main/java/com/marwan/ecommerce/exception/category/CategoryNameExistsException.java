package com.marwan.ecommerce.exception.category;

import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class CategoryNameExistsException extends ValidationException
{
    public CategoryNameExistsException(String name)
    {
        super(101, "Category with name: " + name + " already exists!");
    }
}

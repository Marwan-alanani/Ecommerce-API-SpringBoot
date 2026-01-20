package com.marwan.ecommerce.exception.category;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class CategoryNameExistsException extends ValidationException
{
    public CategoryNameExistsException(String name)
    {
        super(
                ExceptionCodes.CategoryNameExistsException,
                "Category with name: " + name + " already exists!"
        );
    }
}

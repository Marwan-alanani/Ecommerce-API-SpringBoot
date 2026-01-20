package com.marwan.ecommerce.exception.category;

import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class CategoryIdNotFoundException extends NotFoundException
{
    public CategoryIdNotFoundException(UUID id)
    {
        super(101, "Category with id " + id + " not found!");
    }
}

package com.marwan.ecommerce.exception.supplier;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class SupplierNameExistsException extends ValidationException
{
    public SupplierNameExistsException(String name)
    {
        super(ExceptionCodes.SupplierNameExistsException,
                "Supplier with name: " + name + " already exists!");
    }
}

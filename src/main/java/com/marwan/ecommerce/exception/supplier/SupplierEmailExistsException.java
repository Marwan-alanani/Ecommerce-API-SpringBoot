package com.marwan.ecommerce.exception.supplier;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class SupplierEmailExistsException extends ValidationException
{
    public SupplierEmailExistsException(String email)
    {
        super(ExceptionCodes.SupplierEmailExistsException,
                "Supplier with email: " + email + " already exists!");
    }
}

package com.marwan.ecommerce.exception.supplier;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class SupplierNotFoundException extends NotFoundException
{
    public SupplierNotFoundException(UUID supplierId)
    {
        super(ExceptionCodes.SupplierNotFoundException,
                "Supplier with id: " + supplierId + " not found!");
    }
}

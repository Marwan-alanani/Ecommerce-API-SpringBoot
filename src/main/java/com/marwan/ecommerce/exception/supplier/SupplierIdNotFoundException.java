package com.marwan.ecommerce.exception.supplier;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class SupplierIdNotFoundException extends NotFoundException
{
    public SupplierIdNotFoundException(UUID supplierId)
    {
        super(ExceptionCodes.SupplierIdNotFoundException,
                "Supplier with id: " + supplierId + " not found!");
    }
}

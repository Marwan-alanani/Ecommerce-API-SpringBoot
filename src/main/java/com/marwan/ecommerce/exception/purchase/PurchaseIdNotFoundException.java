package com.marwan.ecommerce.exception.purchase;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class PurchaseIdNotFoundException extends NotFoundException
{
    public PurchaseIdNotFoundException(UUID purchaseId)
    {
        super(ExceptionCodes.PurchaseIdNotFoundException, "Purchase with id: " + purchaseId + " " +
                "not found!");
    }
}

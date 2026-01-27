package com.marwan.ecommerce.exception.purchase;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class PurchaseNotFoundException extends NotFoundException
{
    public PurchaseNotFoundException(UUID purchaseId)
    {
        super(ExceptionCodes.PurchaseNotFoundException, "Purchase with id: " + purchaseId + " " +
                "not found!");
    }
}

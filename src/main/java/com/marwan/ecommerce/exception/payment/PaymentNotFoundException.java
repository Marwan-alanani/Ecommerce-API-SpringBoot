package com.marwan.ecommerce.exception.payment;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class PaymentNotFoundException extends NotFoundException
{
    public PaymentNotFoundException(UUID paymentId)
    {
        super(
                ExceptionCodes.PaymentNotFoundException,
                "Payment with the given id " + paymentId.toString() + " not found!"
        );
    }
}

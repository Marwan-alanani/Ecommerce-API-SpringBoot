package com.marwan.ecommerce.exception.order;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.ValidationException;

public class PaymentException extends ValidationException
{
    public PaymentException()
    {
        super(ExceptionCodes.PaymentException, "a payment exception occured");
    }
}

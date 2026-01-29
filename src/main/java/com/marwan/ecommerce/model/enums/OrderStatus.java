package com.marwan.ecommerce.model.enums;

public enum OrderStatus
{
    CREATED, // order created
    PAYMENT_PENDING, // payment initiated
    PAID, // payment successful
    PAYMENT_FAILED
}

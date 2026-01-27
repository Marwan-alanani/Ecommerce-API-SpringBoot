package com.marwan.ecommerce.exception.order;

import com.marwan.ecommerce.exception.ExceptionCodes;
import com.marwan.ecommerce.exception.abstractions.NotFoundException;

import java.util.UUID;

public class OrderNotFoundException extends NotFoundException
{
    public OrderNotFoundException(UUID orderId)
    {
        super(
                ExceptionCodes.OrderNotFoundException,
                "Order with id : " + orderId.toString() + " was not found!"
        );
    }
}

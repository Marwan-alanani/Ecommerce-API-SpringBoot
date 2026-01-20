package com.marwan.ecommerce.exception.abstractions;

public abstract class NotFoundException extends CustomException
{
    public NotFoundException(int code , String message)
    {
        super(code, message);
    }
}

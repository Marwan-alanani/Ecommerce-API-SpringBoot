package com.marwan.ecommerce.exception.abstractions;

public abstract class ValidationException extends CustomException
{
    public ValidationException(int code,String message)
    {
        super(code,message);
    }
}

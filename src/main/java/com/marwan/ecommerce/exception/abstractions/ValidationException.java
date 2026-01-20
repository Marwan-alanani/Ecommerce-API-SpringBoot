package com.marwan.ecommerce.exception.abstractions;

public abstract class ValidationException extends CustomException
{
    public ValidationException(String code,String message)
    {
        super(code,message);
    }
}

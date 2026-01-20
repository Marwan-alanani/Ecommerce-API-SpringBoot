package com.marwan.ecommerce.exception.abstractions;

import lombok.Getter;

public abstract class CustomException extends RuntimeException
{
    @Getter
    private int code;

    public CustomException(int code, String message)
    {
        this.code = code;
        super(message);
    }
}

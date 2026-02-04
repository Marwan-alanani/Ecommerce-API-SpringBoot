package com.marwan.ecommerce.exception.abstractions;

import lombok.Getter;

public abstract class CustomException extends RuntimeException
{
    @Getter
    private final String code;

    public CustomException(String code, String message)
    {
        this.code = code;
        super(message);
    }
}

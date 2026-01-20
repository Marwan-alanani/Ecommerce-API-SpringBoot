package com.marwan.ecommerce.service.user.query;

public record LoginQuery(
        String email,
        String password
)
{
}

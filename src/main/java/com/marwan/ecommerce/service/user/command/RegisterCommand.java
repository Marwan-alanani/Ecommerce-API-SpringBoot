package com.marwan.ecommerce.service.user.command;

public record RegisterCommand(
        String firstName,
        String lastName,
        String email,
        String password
)
{
}

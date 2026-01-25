package com.marwan.ecommerce.controller.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String firstName,

        @NotBlank
        @Size(min = 3, max = 50)
        String lastName,
        @NotBlank(message="Email cannot be blank")
        @Email(message = "Invalid email")
        String email,
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password
)
{
}


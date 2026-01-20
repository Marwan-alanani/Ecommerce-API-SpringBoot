package com.marwan.ecommerce.controller.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterRequest(
        @NotBlank
        String firstName,
        String lastName,
        @Email
        String email,
        @Length(min = 8, max = 100)
        String password
) {
}


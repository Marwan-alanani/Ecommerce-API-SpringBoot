package com.marwan.ecommerce.dto.user;

import java.util.UUID;

public record UserDto(
        UUID userId,
        String firstName,
        String lastName,
        String email,
        boolean isEnabled
)
{
}


package com.marwan.ecommerce.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        boolean isEnabled
) {
}


package com.marwan.ecommerce.dto;

public record AuthenticationDto(
        String firstName,
        String lastName,
        String email,
        String token
) {
}

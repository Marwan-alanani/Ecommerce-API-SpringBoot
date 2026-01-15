package com.example.ecommerce.dto;

public record AuthenticationDto(
        String firstName,
        String lastName,
        String email,
        String token
) {
}

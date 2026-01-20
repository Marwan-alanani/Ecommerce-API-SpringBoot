package com.marwan.ecommerce.dto.user;

public record AuthenticationDto(
        String email,
        String role,
        String token
) {
}

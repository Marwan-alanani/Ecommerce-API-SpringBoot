package com.marwan.ecommerce.controllers.requests;

public record LoginRequest(
        String email,
        String password
) {
}

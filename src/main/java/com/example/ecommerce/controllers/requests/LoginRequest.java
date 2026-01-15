package com.example.ecommerce.controllers.requests;

public record LoginRequest(
        String email,
        String password
) {
}

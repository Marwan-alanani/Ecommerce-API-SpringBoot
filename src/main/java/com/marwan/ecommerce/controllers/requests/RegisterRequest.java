package com.marwan.ecommerce.controllers.requests;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}


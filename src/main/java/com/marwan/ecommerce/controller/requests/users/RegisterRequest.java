package com.marwan.ecommerce.controller.requests.users;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}


package com.marwan.ecommerce.controllers.requests.users;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}


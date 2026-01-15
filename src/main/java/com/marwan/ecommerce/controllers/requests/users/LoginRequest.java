package com.marwan.ecommerce.controllers.requests.users;

public record LoginRequest(
        String email,
        String password
) {
}

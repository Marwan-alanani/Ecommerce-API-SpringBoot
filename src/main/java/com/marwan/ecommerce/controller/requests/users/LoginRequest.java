package com.marwan.ecommerce.controller.requests.users;

public record LoginRequest(
        String email,
        String password
) {
}

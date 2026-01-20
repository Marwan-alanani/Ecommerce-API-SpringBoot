package com.marwan.ecommerce.controller.user.request;

public record LoginRequest(
        String email,
        String password
) {
}

package com.example.ecommerce.security;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String generate();

    String extractUsername(String token);

    String extractRole(String token);

    Claims extractAllClaims(String token);
}

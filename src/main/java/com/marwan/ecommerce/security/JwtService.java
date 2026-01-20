package com.marwan.ecommerce.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService
{
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public boolean isTokenValid(String token)
    {
        if (token == null || token.trim().isEmpty())
            return false;

        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .requireIssuer("Ecommerce App")
                    .requireAudience("localhost")
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }


    }

    public String generateToken(Authentication authentication)
    {
        Map<String, Object> claims = new HashMap<>();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails instanceof CustomUserDetails cu ? cu.getUserId().toString() : userDetails.getUsername();

        claims.put("role", userDetails.getAuthorities().stream().findFirst().get().getAuthority());
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .id(UUID.randomUUID().toString())
                .claim(
                        "role",
                        userDetails.getAuthorities().stream().findFirst().get().getAuthority())
                .claim(
                        "email",
                        userDetails.getUsername())
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(secretKey)
                .issuer("Ecommerce App")
                .audience().add("localhost").and()
                .compact();
    }

    public String extractEmail(String token)
    {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("email");

    }
    public String extractId(String token)
    {
        Claims claims = extractAllClaims(token);
        return claims.getId();

    }

    public String extractRole(String token)
    {
        Claims claims = extractAllClaims(token);
        return (String) (claims.get("role"));
    }

    public Claims extractAllClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

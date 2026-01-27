package com.marwan.ecommerce.security;

import com.marwan.ecommerce.config.JwtConfig;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService
{
    private final JwtConfig jwtConfig;

    public boolean isTokenValid(String token)
    {
        if (token == null || token.trim().isEmpty())
            return false;

        try {
            Jwts.parser()
                    .verifyWith(jwtConfig.getSecretKey())
                    .requireIssuer(jwtConfig.getIssuer())
                    .requireAudience(jwtConfig.getAudience())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return true;
        } catch (JwtException e) {
            return false;
        }


    }

    public String generateAccessToken(CustomUserDetails userDetails)
    {
        return generateToken(userDetails, jwtConfig.getAccessTokenExpirationInSeconds());
    }

    public String generateRefreshToken(CustomUserDetails userDetails)
    {
        return generateToken(userDetails, jwtConfig.getRefreshTokenExpirationInSeconds());
    }

    private String generateToken(CustomUserDetails userDetails, int expirationInSeconds)
    {
        var role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .id(UUID.randomUUID().toString())
                .claim(
                        "role",
                        role)
                .claim(
                        "email",
                        userDetails.getUsername())
                .subject(userDetails.getUserId().toString())
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis() + expirationInSeconds * 1000
                        ))
                .signWith(jwtConfig.getSecretKey())
                .issuer(jwtConfig.getIssuer())
                .audience().add(jwtConfig.getAudience()).and()
                .compact();
    }

    public String extractEmail(String token)
    {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("email");

    }

    public String extractUserId(String token)
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
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

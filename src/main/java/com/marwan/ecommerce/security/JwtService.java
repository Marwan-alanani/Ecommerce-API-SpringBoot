package com.marwan.ecommerce.security;

import com.marwan.ecommerce.config.JwtConfig;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService
{
    private final JwtConfig jwtConfig;

    public boolean isValidAccessToken(String token)
    {
        if (token == null || token.trim().isEmpty())
            return false;

        try {
            var header = Jwts.parser()
                    .verifyWith(jwtConfig.getSecretKey())
                    .requireIssuer(jwtConfig.getIssuer())
                    .requireAudience(jwtConfig.getAudience())
                    .build()
                    .parseSignedClaims(token)
                    .getHeader();
            return header.getType().equals(jwtConfig.getAccessTokenType());
        } catch (JwtException e) {
            return false;
        }
    }


    public boolean isValidRefreshToken(String token)
    {
        if (token == null || token.trim().isEmpty())
            return false;

        try {
            var header = Jwts.parser()
                    .verifyWith(jwtConfig.getSecretKey())
                    .requireIssuer(jwtConfig.getIssuer())
                    .requireAudience(jwtConfig.getAudience())
                    .build()
                    .parseSignedClaims(token)
                    .getHeader();
            return header.getType().equals(jwtConfig.getRefreshTokenType());
        } catch (JwtException e) {
            return false;
        }
    }

    public String generateAccessToken(CustomUserDetails userDetails)
    {
        return generateToken(userDetails,
                jwtConfig.getAccessTokenExpirationInSeconds(),
                jwtConfig.getAccessTokenType()
        );

    }

    public String generateRefreshToken(CustomUserDetails userDetails)
    {
        return generateToken(userDetails,
                jwtConfig.getRefreshTokenExpirationInSeconds(),
                jwtConfig.getRefreshTokenType()
        );

    }

    private String generateToken(
            CustomUserDetails userDetails,
            int expirationInSeconds,
            String type)
    {
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(Objects::nonNull)
                .filter(a -> a.startsWith("ROLE_"))
                .findFirst()
                .orElse("ROLE_USER");

        return Jwts.builder()
                .header()
                .type(type)
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
                                System.currentTimeMillis() + expirationInSeconds * 1000L
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

    public UUID extractUserId(String token)
    {
        Claims claims = extractAllClaims(token);
        return UUID.fromString(claims.getSubject());

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
                .requireIssuer(jwtConfig.getIssuer())
                .requireAudience(jwtConfig.getAudience())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

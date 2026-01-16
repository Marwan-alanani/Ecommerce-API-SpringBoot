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
public class JwtService {
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "super-secret-signing-key-super-secret-signing-key"
                    .getBytes(StandardCharsets.UTF_8));

    public String generateToken(Authentication authentication) {

        Map<String, Object> claims = new HashMap<>();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails instanceof CustomUserDetails cu ?
                cu.getUserId().toString() :
                userDetails.getUsername();

        claims.put("role", userDetails.getAuthorities().stream().findFirst().toString());
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setIssuer("Ecommerce App")
                .setAudience("localhost")
                .compact();
    }

    public String extractEmail(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();

    }

    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return (String) (claims.get("role"));
    }

    public Claims extractAllClaims(String token) {

        JwtParserBuilder parserBuilder = Jwts.parser()
                .setSigningKey(secretKey);
        JwtParser parser = parserBuilder.build();
        return parser
                .parseClaimsJws(token)
                .getBody();
    }
}

package com.marwan.ecommerce.security;

import com.marwan.ecommerce.models.users.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    public String generate(User user) {
        Map<String, Object> claims = new HashMap<>();
        var key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode("supergsecretsigningkeysupersecretsigningkey")
        );
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        throw new NotImplementedException();
    }

    public String extractRole(String token) {

        throw new NotImplementedException();
    }

    public Claims extractAllClaims(String token) {

        throw new NotImplementedException();
    }
}

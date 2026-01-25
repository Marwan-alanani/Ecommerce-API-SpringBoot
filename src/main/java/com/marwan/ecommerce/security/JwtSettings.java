package com.marwan.ecommerce.security;


import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@ConfigurationProperties(prefix = "spring.jwt")
@Data
public class JwtSettings
{
    private String issuer;
    private String audience;
    private String secretKey;
    private int accessTokenExpirationInSeconds;
    private int refreshTokenExpirationInSeconds;

    public SecretKey getSecretKey()
    {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}

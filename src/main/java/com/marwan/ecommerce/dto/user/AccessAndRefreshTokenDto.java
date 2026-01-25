package com.marwan.ecommerce.dto.user;

public record AccessAndRefreshTokenDto(
        String accessToken,
        String refreshToken
)
{
}

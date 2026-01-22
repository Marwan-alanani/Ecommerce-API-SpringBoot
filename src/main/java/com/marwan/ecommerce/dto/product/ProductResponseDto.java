package com.marwan.ecommerce.dto.product;

import java.util.UUID;

public record ProductResponseDto(
        UUID productId,
        String name,
        String description,
        double price,
        String pictureUrl,
        int balance,
        UUID categoryId
)
{
}

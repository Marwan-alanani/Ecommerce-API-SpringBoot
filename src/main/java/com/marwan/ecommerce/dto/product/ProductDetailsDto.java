package com.marwan.ecommerce.dto.product;

import java.util.UUID;

public record ProductDetailsDto(
        UUID productId,
        String name,
        String description,
        double price,

        String pictureUrl,
        int balance,
        String categoryName
)
{
}

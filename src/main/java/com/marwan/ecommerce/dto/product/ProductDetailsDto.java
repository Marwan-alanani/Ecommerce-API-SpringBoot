package com.marwan.ecommerce.dto.product;

import java.util.UUID;

public record ProductDetailsDto(
        UUID id,
        String name,
        String description,
        double price,

        String pictureUrl,
        int balance
        // will add this when the category entity is done
//        String category
)
{
}

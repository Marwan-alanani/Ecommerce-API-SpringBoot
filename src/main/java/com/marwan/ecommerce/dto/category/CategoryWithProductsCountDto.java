package com.marwan.ecommerce.dto.category;

import java.util.UUID;

public record CategoryWithProductsCountDto(
        UUID id,
        String name,
        int productCount
)
{
}

package com.marwan.ecommerce.dto.category;

import java.util.UUID;

public record CategoryWithProductsCountDto(
        UUID categoryId,
        String name,
        int productCount
)
{
}

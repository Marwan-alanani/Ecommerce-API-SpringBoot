package com.marwan.ecommerce.dto.category;

import java.util.UUID;

public record CategoryResponseDto(
        UUID categoryId,
        String name
)
{
}

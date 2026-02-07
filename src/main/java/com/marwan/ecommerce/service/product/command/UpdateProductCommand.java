package com.marwan.ecommerce.service.product.command;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductCommand(
        UUID productId,
        String name,
        String description,
        BigDecimal price,
        String pictureUrl,
        UUID categoryId
)
{
}

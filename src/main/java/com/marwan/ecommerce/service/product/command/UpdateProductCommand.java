package com.marwan.ecommerce.service.product.command;

import java.util.UUID;

public record UpdateProductCommand(
        UUID productId,
        String name,
        String description,
        double price,
        String pictureUrl,
        UUID categoryId,
        double maxPurchasePrice
)
{
}

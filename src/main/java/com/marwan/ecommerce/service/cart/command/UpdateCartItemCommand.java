package com.marwan.ecommerce.service.cart.command;

import java.util.UUID;

public record UpdateCartItemCommand(
        UUID productId,
        UUID userId,
        int quantity
)
{
}

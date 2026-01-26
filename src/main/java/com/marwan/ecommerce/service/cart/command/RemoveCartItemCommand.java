package com.marwan.ecommerce.service.cart.command;

import java.util.UUID;

public record RemoveCartItemCommand(
        UUID userId,
        UUID productId

)
{
}

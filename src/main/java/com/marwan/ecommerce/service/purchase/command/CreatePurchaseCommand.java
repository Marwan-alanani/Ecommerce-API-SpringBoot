package com.marwan.ecommerce.service.purchase.command;

import java.util.UUID;

public record CreatePurchaseCommand(
        UUID productId,
        double price,
        int quantity,
        UUID supplierId
)
{
}

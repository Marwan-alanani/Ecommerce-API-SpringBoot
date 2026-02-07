package com.marwan.ecommerce.service.purchase.command;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePurchaseCommand(
        UUID productId,
        BigDecimal unitPrice,
        int quantity,
        UUID supplierId
)
{
}

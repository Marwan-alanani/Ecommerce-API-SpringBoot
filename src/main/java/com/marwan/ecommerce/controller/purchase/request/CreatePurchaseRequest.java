package com.marwan.ecommerce.controller.purchase.request;

import java.util.UUID;

public record CreatePurchaseRequest(
        UUID productId,
        double price,
        int quantity,
        UUID supplierId
)
{
}

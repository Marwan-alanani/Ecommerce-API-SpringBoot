package com.marwan.ecommerce.service.purchase.event;

import java.util.UUID;

public record PurchaseCreatedEvent(
        UUID productId,
        double price
)
{
}

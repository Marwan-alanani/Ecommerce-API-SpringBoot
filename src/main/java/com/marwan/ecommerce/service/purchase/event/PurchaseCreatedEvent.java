package com.marwan.ecommerce.service.purchase.event;

import java.math.BigDecimal;
import java.util.UUID;

public record PurchaseCreatedEvent(
        UUID productId,
        BigDecimal unitPrice,
        int quantity

)
{
}

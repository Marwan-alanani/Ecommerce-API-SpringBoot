package com.marwan.ecommerce.dto.purchase;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PurchaseDto(
        UUID purchaseId,
        UUID productId,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal totalCost,
        UUID supplierId,
        Instant createdDateTime
)
{

}

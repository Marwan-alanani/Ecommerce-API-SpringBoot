package com.marwan.ecommerce.dto.payment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record UserPaymentDto(
        UUID paymentId,
        UUID orderId,
        String provider,
        BigDecimal amount,
        String currency,
        Instant createdDateTime,
        Instant updatedDateTime,
        String status
)
{
}

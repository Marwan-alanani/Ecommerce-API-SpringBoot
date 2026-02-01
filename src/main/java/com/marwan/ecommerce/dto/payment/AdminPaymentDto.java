package com.marwan.ecommerce.dto.payment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AdminPaymentDto(
        UUID paymentId,
        String checkoutSessionId,
        UUID orderId,
        UUID userId,
        BigDecimal amount,
        String currency,
        Instant createdDateTime,
        Instant updatedDateTime,
        String status
)

{
}

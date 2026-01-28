package com.marwan.ecommerce.dto.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderDto(
        UUID orderId,
        UUID userId,
        List<OrderItemDto> orderItems,
        BigDecimal totalPrice,
        Instant createdDateTime,
        String orderStatus
)
{
}

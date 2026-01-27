package com.marwan.ecommerce.dto.order;

import java.util.UUID;

public record CheckoutResponseDto(
        UUID orderId
)
{
}

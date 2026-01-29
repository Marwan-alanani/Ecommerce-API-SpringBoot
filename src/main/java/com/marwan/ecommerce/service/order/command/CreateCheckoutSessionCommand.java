package com.marwan.ecommerce.service.order.command;

import java.util.List;
import java.util.UUID;

public record CreateCheckoutSessionCommand(
        UUID paymentId,
        String currency,
        List<LineItemDto> items
)
{
}


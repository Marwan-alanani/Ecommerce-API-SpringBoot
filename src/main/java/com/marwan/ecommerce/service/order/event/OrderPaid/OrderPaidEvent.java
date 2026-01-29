package com.marwan.ecommerce.service.order.event.OrderPaid;

import java.util.UUID;

public record OrderPaidEvent(
        UUID orderId
)
{
}

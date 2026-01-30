package com.marwan.ecommerce.service.order.event.orderPaid;

import java.util.UUID;

public record OrderPaidEvent(
        UUID orderId
)
{
}

package com.marwan.ecommerce.controller.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDto(
        UUID orderItemId,
        String productName,
        String productPictureUrl,
        BigDecimal unitPrice,
        int quantity
)
{
}

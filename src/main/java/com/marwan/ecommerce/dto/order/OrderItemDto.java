package com.marwan.ecommerce.dto.order;

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

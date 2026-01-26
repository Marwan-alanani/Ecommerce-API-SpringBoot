package com.marwan.ecommerce.dto.cart;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemDto(
        UUID cartItemId,
        String productName,
        BigDecimal unitPrice,
        int quantity

)
{
}

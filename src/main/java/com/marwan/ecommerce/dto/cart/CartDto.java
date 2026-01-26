package com.marwan.ecommerce.dto.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CartDto(
        UUID userId,
        List<CartItemDto> cartItems,
        BigDecimal totalCost
)
{
}

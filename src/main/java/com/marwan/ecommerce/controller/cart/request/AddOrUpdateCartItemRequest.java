package com.marwan.ecommerce.controller.cart.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddOrUpdateCartItemRequest(
        @NotNull(message="Product id is required")
        UUID productId,

        int quantity
)
{
}

package com.marwan.ecommerce.controller.cart.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record AddCartItemRequest(
        @NotNull(message = "Product id is required")
        UUID productId,
        @Positive(message = "quantity must be greater than zero")
        int quantity
)
{
}

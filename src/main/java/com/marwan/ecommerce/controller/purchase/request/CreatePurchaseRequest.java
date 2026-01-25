package com.marwan.ecommerce.controller.purchase.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreatePurchaseRequest(
        @NotNull(message = "Product id is required")
        UUID productId,

        @NotNull(message = "Purchase price is required")
        @Positive(message = "Purchase price must be greater than zero")
        double price,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        int quantity,
        @NotNull(message = "Supplier id is required")
        UUID supplierId
)
{
}

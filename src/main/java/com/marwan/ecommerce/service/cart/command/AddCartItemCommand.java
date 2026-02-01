package com.marwan.ecommerce.service.cart.command;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AddCartItemCommand
{
    private UUID userId;
    private UUID productId;
    @Positive(message = "quantity must be greater than zero")
    private int quantity;

    public AddCartItemCommand(UUID productId, UUID userId)
    {
        this.productId = productId;
        this.userId = userId;
        this.quantity = 1;
    }
}

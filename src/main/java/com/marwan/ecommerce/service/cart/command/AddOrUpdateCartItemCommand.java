package com.marwan.ecommerce.service.cart.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AddOrUpdateCartItemCommand
{
    private UUID userId;
    private UUID productId;
    private int quantity;

    public AddOrUpdateCartItemCommand(UUID productId, UUID userId)
    {
        this.productId = productId;
        this.userId = userId;
        this.quantity = 1;
    }
}

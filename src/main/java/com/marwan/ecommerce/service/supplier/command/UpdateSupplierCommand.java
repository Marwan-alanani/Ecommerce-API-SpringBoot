package com.marwan.ecommerce.service.supplier.command;

import java.util.UUID;

public record UpdateSupplierCommand(
        UUID supplierId,
        String name,
        String email
)
{
}

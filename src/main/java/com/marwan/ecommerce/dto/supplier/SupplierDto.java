package com.marwan.ecommerce.dto.supplier;

import java.util.UUID;

public record SupplierDto(
        UUID supplierId,
        String name,
        String email
)
{
}

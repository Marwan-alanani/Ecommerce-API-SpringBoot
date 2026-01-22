package com.marwan.ecommerce.controller.supplier.request;

import java.util.UUID;

public record UpdateSupplierRequest(
        UUID supplierId,
        String name,
        String email
)
{
}

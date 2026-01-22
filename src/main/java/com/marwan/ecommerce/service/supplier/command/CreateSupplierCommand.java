package com.marwan.ecommerce.service.supplier.command;

public record CreateSupplierCommand(
        String name,
        String email
)
{
}

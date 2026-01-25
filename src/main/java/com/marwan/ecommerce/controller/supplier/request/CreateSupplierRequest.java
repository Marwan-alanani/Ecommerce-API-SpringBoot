package com.marwan.ecommerce.controller.supplier.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateSupplierRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 255, min = 3, message = "Supplier name must be between 3 and 255")
        String name,

        @NotBlank
        @Email(message = "Invalid email")
        String email
)
{
}

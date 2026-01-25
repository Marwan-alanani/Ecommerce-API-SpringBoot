package com.marwan.ecommerce.controller.product.request;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

public record CreateProductRequest(
        @NotBlank(message = "Product name cannot be blank")
        @Size(max = 255, min = 3, message = "Product name must be between 3 to 255 characters")
        String name,

        @Size(max = 1000, message = "Description must not exceed 1000 character")
        String description,

        @NotNull(message = "Product price is required")
        @Positive(message = "Product price must be greater than zero")
        double price,

        @NotBlank(message = "Picture URL cannot be blank")
        @URL(message = "Picture URL must be a valid URL")
        String pictureUrl,

        UUID categoryId
)
{
}

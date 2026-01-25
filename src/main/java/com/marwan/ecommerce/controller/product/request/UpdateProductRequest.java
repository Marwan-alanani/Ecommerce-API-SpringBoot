package com.marwan.ecommerce.controller.product.request;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

public record UpdateProductRequest(

        @NotNull(message = "Product id is required")
        UUID productId,
        @Size(max = 255, min = 3, message = "Product name must be between 3 to 255 characters")
        String name,
        @Size(max = 1000, message = "Description must not exceed 1000 character")
        String description,
        @Positive(message = "Product price must be greater than zero")
        double price,
        @URL(message = "Picture URL must be a valid URL")
        String pictureUrl,
        UUID categoryId
)
{
}

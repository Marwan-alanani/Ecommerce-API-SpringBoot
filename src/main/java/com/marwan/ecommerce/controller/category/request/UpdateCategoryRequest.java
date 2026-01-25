package com.marwan.ecommerce.controller.category.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UpdateCategoryRequest(
        @NotBlank(message = "Category Id cannot be blank")
        UUID categoryId,
        @NotBlank(message = "Category name cannot be blank")
        String name
)
{
}

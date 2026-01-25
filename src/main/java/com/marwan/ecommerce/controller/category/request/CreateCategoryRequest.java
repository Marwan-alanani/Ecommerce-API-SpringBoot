package com.marwan.ecommerce.controller.category.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank(message = "Category name cannot be blank")
        String name
)
{
}

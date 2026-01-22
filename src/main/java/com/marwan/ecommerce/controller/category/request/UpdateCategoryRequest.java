package com.marwan.ecommerce.controller.category.request;

import java.util.UUID;

public record UpdateCategoryRequest(
        UUID categoryId,
        String name
)
{
}

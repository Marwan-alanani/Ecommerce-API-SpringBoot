package com.marwan.ecommerce.service.category.command;

import java.util.UUID;

public record UpdateCategoryCommand(
        UUID id,
        String name
)
{
}

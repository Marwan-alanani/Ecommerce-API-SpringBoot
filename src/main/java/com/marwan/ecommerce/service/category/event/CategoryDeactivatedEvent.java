package com.marwan.ecommerce.service.category.event;

import java.util.UUID;

public record CategoryDeactivatedEvent(
        UUID categoryId
)
{
}

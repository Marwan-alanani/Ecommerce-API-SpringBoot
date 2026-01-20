package com.marwan.ecommerce.controller.product.request;

import java.util.UUID;

public record UpdateProductRequest(

        UUID id,
        String name,
        String description,
        double price,
        String pictureUrl,
        UUID categoryId
)
{
}

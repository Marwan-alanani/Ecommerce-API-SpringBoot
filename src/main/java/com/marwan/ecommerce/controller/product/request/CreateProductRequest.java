package com.marwan.ecommerce.controller.product.request;

import java.util.UUID;

public record CreateProductRequest(
       String name,
       String description,
       double price,
       String pictureUrl,
       UUID categoryId
)
{
}

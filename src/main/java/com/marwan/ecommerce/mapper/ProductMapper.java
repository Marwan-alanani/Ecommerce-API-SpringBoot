package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.controller.product.request.CreateProductRequest;
import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.model.product.entity.Product;
import com.marwan.ecommerce.service.product.command.CreateProductCommand;

public class ProductMapper
{
    public static ProductDetailsDto mapProductToProductDetailsDto(Product product)
    {
        return new ProductDetailsDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getPictureUrl(),
                product.getBalance()
        );
    }

    public static CreateProductCommand mapCreateProductRequestToCreateProductCommand(
            CreateProductRequest request)
    {
        return new CreateProductCommand(
                request.name(),
                request.description(),
                request.price(),
                request.pictureUrl(),
                request.categoryId()
        );
    }
}

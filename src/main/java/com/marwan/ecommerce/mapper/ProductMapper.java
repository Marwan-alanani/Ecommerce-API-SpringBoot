package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.controller.product.request.CreateProductRequest;
import com.marwan.ecommerce.controller.product.request.UpdateProductRequest;
import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.dto.product.ProductResponseDto;
import com.marwan.ecommerce.model.product.entity.Product;
import com.marwan.ecommerce.service.product.command.CreateProductCommand;
import com.marwan.ecommerce.service.product.command.UpdateProductCommand;

public class ProductMapper
{
    public static ProductDetailsDto mapProductToProductDetailsDto(
            Product product,
            String categoryName)
    {
        return new ProductDetailsDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getPictureUrl(),
                product.getBalance(),
                categoryName
        );
    }

    public static ProductResponseDto mapProductToProductResponseDto(
            Product product)
    {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getPictureUrl(),
                product.getBalance(),
                product.getCategoryId()
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
    public static UpdateProductCommand mapUpdateProductRequestToUpdateProductCommand(
            UpdateProductRequest request)
    {
        return new UpdateProductCommand(
                request.id(),
                request.name(),
                request.description(),
                request.price(),
                request.pictureUrl(),
                request.categoryId()
        );
    }
}

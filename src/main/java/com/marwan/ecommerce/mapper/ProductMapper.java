package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.controller.product.request.CreateProductRequest;
import com.marwan.ecommerce.controller.product.request.UpdateProductRequest;
import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.dto.product.ProductResponseDto;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.service.product.command.CreateProductCommand;
import com.marwan.ecommerce.service.product.command.UpdateProductCommand;
import com.marwan.ecommerce.service.purchase.event.PurchaseCreatedEvent;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper
{


    UpdateProductCommand updateProductRequestToUpdateProductCommand(
            UpdateProductRequest request);

    CreateProductCommand createProductRequestToCreateProductCommand(
            CreateProductRequest request);

    @Mapping(target = "categoryId", source = "category.categoryId")
    ProductResponseDto productToProductResponseDto(Product product);

    List<ProductResponseDto> productListToProductResponseDtoList(List<Product> productList);

    ProductDetailsDto productToProductDetailsDto(Product product, String categoryName);

    @Mapping(target = "updatedDateTime", expression = "java(java.time.LocalDateTime.now())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromCommand(
            @MappingTarget Product product,
            UpdateProductCommand command);
}
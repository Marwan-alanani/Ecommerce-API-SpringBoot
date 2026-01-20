package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.controller.category.request.CreateCategoryRequest;
import com.marwan.ecommerce.controller.category.request.UpdateCategoryRequest;
import com.marwan.ecommerce.dto.category.CategoryResponseDto;
import com.marwan.ecommerce.dto.category.CategoryWithProductsCountDto;
import com.marwan.ecommerce.model.category.Category;
import com.marwan.ecommerce.service.category.command.CreateCategoryCommand;
import com.marwan.ecommerce.service.category.command.UpdateCategoryCommand;

public class CategoryMapper
{
    public static CreateCategoryCommand mapCreateCategoryRequestToCreateCategoryCommand(
            CreateCategoryRequest request)
    {
        return new CreateCategoryCommand(
                request.name()
        );
    }

    public static UpdateCategoryCommand mapUpdateCategoryRequestToUpdateCategoryCommand(
            UpdateCategoryRequest request)
    {
        return new UpdateCategoryCommand(
                request.id(),
                request.name()
        );
    }

    public static CategoryResponseDto mapCategoryToCategoryResponseDto(Category category)
    {
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }

    public static CategoryWithProductsCountDto mapCategoryToCategoryWithProductCountDto(
            Category category,
            int productCount)
    {
        return new CategoryWithProductsCountDto(
                category.getId(),
                category.getName(),
                productCount
        );
    }
}

package com.marwan.ecommerce.mapper;
import com.marwan.ecommerce.controller.category.request.CreateCategoryRequest;
import com.marwan.ecommerce.controller.category.request.UpdateCategoryRequest;
import com.marwan.ecommerce.dto.category.CategoryResponseDto;
import com.marwan.ecommerce.dto.category.CategoryWithProductsCountDto;
import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.service.category.command.CreateCategoryCommand;
import com.marwan.ecommerce.service.category.command.UpdateCategoryCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper
{
    CategoryResponseDto categoryToCategoryResponseDto(Category category);

    List<CategoryResponseDto> categoryListToCategoryResponseDtoList(List<Category> categoryList);

    CreateCategoryCommand createCategoryRequestToCreateCategoryCommand(
            CreateCategoryRequest request);

    UpdateCategoryCommand updateCategoryRequestToUpdateCategoryCommand(
            UpdateCategoryRequest request);

    default CategoryWithProductsCountDto categoryAndProductCountToCategoryWithProductsCountDto(
            Category category,
            int productCount)
    {
        return new CategoryWithProductsCountDto(
                category.getCategoryId(),
                category.getName(),
                productCount);
    }

    @Mapping(target = "updatedDateTime", expression = "java(new java.util.Date())")
    void updateFromCommand(@MappingTarget Category category, UpdateCategoryCommand command);
}
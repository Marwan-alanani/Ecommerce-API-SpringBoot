package com.marwan.ecommerce.controller.category;

import com.marwan.ecommerce.controller.category.request.CreateCategoryRequest;
import com.marwan.ecommerce.controller.category.request.UpdateCategoryRequest;
import com.marwan.ecommerce.dto.category.CategoryResponseDto;
import com.marwan.ecommerce.dto.category.CategoryWithProductsCountDto;
import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.exception.category.CategoryIdNotFoundException;
import com.marwan.ecommerce.exception.category.CategoryNameExistsException;
import com.marwan.ecommerce.mapper.CategoryMapper;
import com.marwan.ecommerce.mapper.ProductMapper;
import com.marwan.ecommerce.model.category.Category;
import com.marwan.ecommerce.model.product.entity.Product;
import com.marwan.ecommerce.service.category.CategoryService;
import com.marwan.ecommerce.service.category.command.CreateCategoryCommand;
import com.marwan.ecommerce.service.category.command.UpdateCategoryCommand;
import com.marwan.ecommerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController
{
    private final CategoryService categoryService;
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDto> create(@RequestBody CreateCategoryRequest request)
            throws CategoryNameExistsException
    {
        CreateCategoryCommand command =
                CategoryMapper.mapCreateCategoryRequestToCreateCategoryCommand(request);
        Category category = categoryService.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CategoryMapper.mapCategoryToCategoryResponseDto(category)
        );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryWithProductsCountDto> getCategory(@PathVariable UUID categoryId)
            throws CategoryIdNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.getCategory(categoryId)
        );
    }

    @PostMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID categoryId)
            throws CategoryIdNotFoundException
    {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/update")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @RequestBody UpdateCategoryRequest request)
            throws CategoryIdNotFoundException, CategoryNameExistsException
    {
        UpdateCategoryCommand command =
                CategoryMapper.mapUpdateCategoryRequestToUpdateCategoryCommand(request);

        Category category = categoryService.updateCategory(command);
        return ResponseEntity.status(HttpStatus.OK).body(
                CategoryMapper.mapCategoryToCategoryResponseDto(category)
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories()
    {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();
        categories.forEach(category -> {
            categoryResponseDtos.add(CategoryMapper.mapCategoryToCategoryResponseDto(category));
        });
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDtos);
    }

}

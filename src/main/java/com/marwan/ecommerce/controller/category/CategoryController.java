package com.marwan.ecommerce.controller.category;

import com.marwan.ecommerce.controller.category.request.CreateCategoryRequest;
import com.marwan.ecommerce.controller.category.request.UpdateCategoryRequest;
import com.marwan.ecommerce.dto.category.CategoryResponseDto;
import com.marwan.ecommerce.dto.category.CategoryWithProductsCountDto;
import com.marwan.ecommerce.exception.category.CategoryIdNotFoundException;
import com.marwan.ecommerce.exception.category.CategoryNameExistsException;
import com.marwan.ecommerce.mapper.CategoryMapper;
import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.service.category.CategoryService;
import com.marwan.ecommerce.service.category.command.CreateCategoryCommand;
import com.marwan.ecommerce.service.category.command.UpdateCategoryCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController
{
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDto> create(
            @Valid @RequestBody CreateCategoryRequest request)
            throws CategoryNameExistsException
    {
        CreateCategoryCommand command =
                categoryMapper.createCategoryRequestToCreateCategoryCommand(request);
        Category category = categoryService.create(command, true);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoryMapper.categoryToCategoryResponseDto(category)
        );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryWithProductsCountDto> getCategory(@PathVariable UUID categoryId)
            throws CategoryIdNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.getCategoryWithProductCount(categoryId, true)
        );
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?> deactivateCategory(@PathVariable UUID categoryId)
            throws CategoryIdNotFoundException
    {
        categoryService.deactivateCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @Valid @RequestBody UpdateCategoryRequest request)
            throws CategoryIdNotFoundException, CategoryNameExistsException
    {
        UpdateCategoryCommand command =
                categoryMapper.updateCategoryRequestToUpdateCategoryCommand(request);

        Category category = categoryService.updateCategory(command, true);
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryMapper.categoryToCategoryResponseDto(category)
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories()
    {
        List<Category> categories = categoryService.getAllCategories(true);
        List<CategoryResponseDto> categoryResponseDtoList =
                categoryMapper.categoryListToCategoryResponseDtoList(categories);

        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDtoList);
    }

}

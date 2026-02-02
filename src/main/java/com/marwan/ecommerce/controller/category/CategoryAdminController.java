package com.marwan.ecommerce.controller.category;

import com.marwan.ecommerce.controller.category.request.CreateCategoryRequest;
import com.marwan.ecommerce.controller.category.request.UpdateCategoryRequest;
import com.marwan.ecommerce.dto.category.CategoryPagingOptions;
import com.marwan.ecommerce.dto.category.CategoryResponseDto;
import com.marwan.ecommerce.dto.category.CategoryWithProductsCountDto;
import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.exception.category.CategoryNameExistsException;
import com.marwan.ecommerce.exception.category.CategoryNotFoundException;
import com.marwan.ecommerce.mapper.CategoryMapper;
import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.service.category.CategoryService;
import com.marwan.ecommerce.service.category.command.CreateCategoryCommand;
import com.marwan.ecommerce.service.category.command.UpdateCategoryCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.marwan.ecommerce.controller.common.BaseController.toPageDto;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController
{
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    @PatchMapping
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @Valid @RequestBody UpdateCategoryRequest request)
            throws CategoryNotFoundException, CategoryNameExistsException
    {
        UpdateCategoryCommand command = categoryMapper.updateCategoryRequestToCommand(request);

        Category category = categoryService.updateCategory(command);
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryMapper.categoryToCategoryResponseDto(category)
        );
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deactivateCategory(@PathVariable UUID categoryId)
            throws CategoryNotFoundException
    {
        categoryService.deactivateCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create( // TODO: Uri builder
            @Valid @RequestBody CreateCategoryRequest request)
            throws CategoryNameExistsException
    {
        CreateCategoryCommand command = categoryMapper.createCategoryRequestToCommand(request);
        Category category = categoryService.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoryMapper.categoryToCategoryResponseDto(category)
        );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryWithProductsCountDto> getCategory(@PathVariable UUID categoryId)
            throws CategoryNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.getCategoryWithProductCount(categoryId)
        );
    }

    @GetMapping
    public ResponseEntity<PageDto<CategoryResponseDto>> getAll(
            @Valid CategoryPagingOptions pagingOptions
    )
    {
        Page<Category> categoryPage = categoryService.getActiveCategories(pagingOptions);
        List<CategoryResponseDto> categoryResponseDtoList =
                categoryMapper.categoryListToCategoryResponseDtoList(categoryPage.getContent());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(toPageDto(categoryPage, categoryResponseDtoList));
    }


}

package com.marwan.ecommerce.controller.category;

import com.marwan.ecommerce.dto.category.CategoryPagingOptions;
import com.marwan.ecommerce.dto.category.CategoryResponseDto;
import com.marwan.ecommerce.dto.category.CategoryWithProductsCountDto;
import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.exception.category.CategoryNotFoundException;
import com.marwan.ecommerce.mapper.CategoryMapper;
import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.service.category.CategoryService;
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
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController
{
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryWithProductsCountDto> getCategory(@PathVariable UUID categoryId)
            throws CategoryNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.getActiveCategoryWithProductCount(categoryId)
        );
    }

    @GetMapping
    public ResponseEntity<PageDto<CategoryResponseDto>> getAll(
            @Valid CategoryPagingOptions pagingOptions
    )
    {
        Page<Category> categoryPage = categoryService.getAllCategories(pagingOptions);
        List<CategoryResponseDto> categoryResponseDtoList =
                categoryMapper.categoryListToCategoryResponseDtoList(categoryPage.getContent());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(toPageDto(categoryPage, categoryResponseDtoList));
    }
}

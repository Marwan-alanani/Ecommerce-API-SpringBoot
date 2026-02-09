package com.marwan.ecommerce.service.category;

import com.marwan.ecommerce.dto.category.CategoryWithProductsCountDto;
import com.marwan.ecommerce.exception.category.CategoryNotFoundException;
import com.marwan.ecommerce.exception.category.CategoryNameExistsException;
import com.marwan.ecommerce.mapper.CategoryMapper;
import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.repository.CategoryRepository;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.service.category.command.CreateCategoryCommand;
import com.marwan.ecommerce.service.category.command.UpdateCategoryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;

    @Transactional
    public Category create(CreateCategoryCommand command)
            throws CategoryNameExistsException
    {
        if (categoryRepository.findByName(command.name()).isPresent()) {
            throw new CategoryNameExistsException(command.name());
        }
        Category category = Category.create(command.name());
        categoryRepository.save(category);
        return category;
    }

    public boolean categoryActive(UUID id)
    {
        return categoryRepository.existsByCategoryIdAndIsEnabled(id, true);
    }

    public boolean categoryExists(UUID id)
    {
        return categoryRepository.existsById(id);
    }

    // too complex, might refactor later
    public CategoryWithProductsCountDto getActiveCategoryWithProductCount(
            UUID categoryId)
            throws CategoryNotFoundException
    {
        Category category = categoryRepository.findByCategoryIdAndIsEnabled(categoryId, true)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));


        int productCount = productRepository
                .countByCategory_CategoryIdAndIsEnabled(categoryId, true);

        return categoryMapper.categoryAndProductCountToCategoryWithProductsCountDto(
                category,
                productCount);
    }

    public CategoryWithProductsCountDto getCategoryWithProductCount(
            UUID categoryId)
            throws CategoryNotFoundException
    {
        Category category = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));


        int productCount = productRepository.countByCategory_CategoryId(categoryId);

        return categoryMapper.categoryAndProductCountToCategoryWithProductsCountDto(
                category,
                productCount);
    }

    public Category getActiveCategory(UUID categoryId)
            throws CategoryNotFoundException
    {
        return categoryRepository.findByCategoryIdAndIsEnabled(categoryId, true)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    }

    public Category getCategory(UUID categoryId)
            throws CategoryNotFoundException
    {
        return categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    }

    public void deactivateCategory(UUID categoryId)
            throws CategoryNotFoundException
    {
        Category category = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        category.deactivate();
        categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(UpdateCategoryCommand command)
            throws CategoryNotFoundException, CategoryNameExistsException
    {
        Category category = categoryRepository
                .findByCategoryId(command.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(command.categoryId()));

        int countByName = categoryRepository.countByName(command.name());
        if (countByName > 1) {
            throw new CategoryNameExistsException(command.name());
        }
        if (countByName == 1 && !category.getName().equals(command.name())) {
            throw new CategoryNameExistsException(command.name());
        }
        categoryMapper.updateFromCommand(category, command);
        categoryRepository.save(category);
        return category;
    }

    public Page<Category> getAllCategories(
            Pageable pageable
    )
    {
        return categoryRepository.findAll(pageable);
    }

    public Page<Category> getActiveCategories(Pageable pageable)
    {
        return categoryRepository.findAllByIsEnabled(pageable, true);
    }

}

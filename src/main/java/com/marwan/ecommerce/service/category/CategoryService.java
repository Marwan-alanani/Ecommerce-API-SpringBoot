package com.marwan.ecommerce.service.category;

import com.marwan.ecommerce.dto.category.CategoryPagingOptions;
import com.marwan.ecommerce.dto.category.CategoryWithProductsCountDto;
import com.marwan.ecommerce.exception.category.CategoryNotFoundException;
import com.marwan.ecommerce.exception.category.CategoryNameExistsException;
import com.marwan.ecommerce.mapper.CategoryMapper;
import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.repository.CategoryRepository;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.service.category.command.CreateCategoryCommand;
import com.marwan.ecommerce.service.category.command.UpdateCategoryCommand;
import com.marwan.ecommerce.service.category.event.CategoryDeactivatedEvent;
import com.marwan.ecommerce.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryService extends BaseService
{
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher eventPublisher;
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

    public boolean categoryExists(UUID id, boolean isEnabled)
    {
        return categoryRepository.findByCategoryIdAndIsEnabled(id, isEnabled).isPresent();
    }

    public CategoryWithProductsCountDto getCategoryWithProductCount(
            UUID categoryId,
            boolean isEnabled)
            throws CategoryNotFoundException
    {
        Optional<Category> category = categoryRepository
                .findByCategoryIdAndIsEnabled(categoryId, isEnabled);

        if (category.isEmpty()) {
            throw new CategoryNotFoundException(categoryId);
        }

        int productCount = productRepository
                .countByCategory_CategoryIdAndIsEnabled(categoryId, isEnabled);

        return categoryMapper.categoryAndProductCountToCategoryWithProductsCountDto(
                category.get(),
                productCount);
    }

    public Category getCategory(UUID categoryId, boolean isEnabled)
            throws CategoryNotFoundException
    {
        Optional<Category> category = categoryRepository.findByCategoryIdAndIsEnabled(categoryId,
                isEnabled);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException(categoryId);
        }
        return category.get();
    }

    public void deactivateCategory(UUID categoryId)
            throws CategoryNotFoundException
    {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(categoryId);
        }
        Category category = optionalCategory.get();
        category.setEnabled(false);
        categoryRepository.save(category);
        eventPublisher.publishEvent(new CategoryDeactivatedEvent(categoryId));

    }

    @Transactional
    public Category updateCategory(UpdateCategoryCommand command, boolean isEnabled)
            throws CategoryNotFoundException, CategoryNameExistsException
    {
        Optional<Category> optionalCategory = categoryRepository
                .findByCategoryIdAndIsEnabled(command.categoryId(), isEnabled);

        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(command.categoryId());
        }
        int countByName = categoryRepository.countByName(command.name());
        Category category = optionalCategory.get();
        if (countByName > 0 && !category.getName().equals(command.name())) {
            throw new CategoryNameExistsException(command.name());
        }
        categoryMapper.updateFromCommand(category, command);
        categoryRepository.save(category);
        return category;
    }

    public Page<Category> getAllCategories(
            CategoryPagingOptions pagingOptions,
            boolean isEnabled)
    {
        var pageable = constructPageable(pagingOptions);
        return categoryRepository.findAllByIsEnabled(pageable, isEnabled);
    }

}

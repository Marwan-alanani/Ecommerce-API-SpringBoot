package com.marwan.ecommerce.service.category;

import com.marwan.ecommerce.dto.category.CategoryWithProductsCountDto;
import com.marwan.ecommerce.exception.category.CategoryIdNotFoundException;
import com.marwan.ecommerce.exception.category.CategoryNameExistsException;
import com.marwan.ecommerce.mapper.CategoryMapper;
import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.repository.CategoryRepository;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.service.category.command.CreateCategoryCommand;
import com.marwan.ecommerce.service.category.command.UpdateCategoryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService
{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;

    public Category create(CreateCategoryCommand command) throws CategoryNameExistsException
    {
        if (categoryRepository.findByName(command.name()).isPresent()) {
            throw new CategoryNameExistsException(command.name());
        }
        Category category = Category.create(command.name());
        categoryRepository.save(category);
        return category;

    }

    public boolean categoryExists(UUID id)
    {
        return categoryRepository.findById(id).isPresent();
    }

    public CategoryWithProductsCountDto getCategoryWithProductCount(UUID categoryId)
            throws CategoryIdNotFoundException
    {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new CategoryIdNotFoundException(categoryId);
        }
        int productCount = productRepository.countByCategoryId(categoryId);
        return categoryMapper.categoryAndProductCountToCategoryWithProductsCountDto(
                category.get(),
                productCount);
    }

    public Category getCategory(UUID categoryId)
            throws CategoryIdNotFoundException
    {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new CategoryIdNotFoundException(categoryId);
        }
        return category.get();
    }

    public void deleteCategory(UUID categoryId) throws CategoryIdNotFoundException
    {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new CategoryIdNotFoundException(categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

    public Category updateCategory(UpdateCategoryCommand command)
            throws CategoryIdNotFoundException, CategoryNameExistsException
    {
        Optional<Category> optionalCategory = categoryRepository.findById(command.categoryId());
        if (optionalCategory.isEmpty()) {
            throw new CategoryIdNotFoundException(command.categoryId());
        }
        if (categoryRepository.findByName(command.name()).isPresent()) {
            throw new CategoryNameExistsException(command.name());
        }
        Category category = optionalCategory.get();
        categoryMapper.updateFromCommand(category, command);
        categoryRepository.save(category);
        return category;
    }

    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

}

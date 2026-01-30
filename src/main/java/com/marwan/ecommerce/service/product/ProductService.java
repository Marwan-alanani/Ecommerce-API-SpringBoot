package com.marwan.ecommerce.service.product;

import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.exception.category.CategoryNotFoundException;
import com.marwan.ecommerce.exception.product.ProductNotFoundException;
import com.marwan.ecommerce.mapper.ProductMapper;
import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.model.enums.SortDirection;
import com.marwan.ecommerce.model.enums.sorting.ProductSortingOptions;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.service.category.CategoryService;
import com.marwan.ecommerce.service.product.command.CreateProductCommand;
import com.marwan.ecommerce.service.product.command.UpdateProductCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService
{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    public Product createProduct(CreateProductCommand command)
            throws CategoryNotFoundException
    {
        Category category = null;
        if (command.categoryId() != null) {
            category = categoryService.getCategory(command.categoryId(), true);
        }

        Product product = Product.create(
                command.name(),
                command.description(),
                command.price(),
                command.pictureUrl(),
                category
        );
        productRepository.save(product);
        return product;
    }

    public ProductDetailsDto getProductWithCategoryNameById(UUID id, boolean isEnabled)
            throws ProductNotFoundException
    {
        Product product = productRepository
                .findWithCategoryByProductIdAndIsEnabled(id, isEnabled)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (product.getCategory() == null) {
            return productMapper.productToProductDetailsDto(product, null);
        }
        return productMapper.productToProductDetailsDto(product, product.getCategory().getName());

    }

    public Product getProduct(UUID productId, boolean isEnabled)
            throws ProductNotFoundException
    {
        return productRepository
                .findByProductIdAndIsEnabled(productId, isEnabled)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public boolean productExists(UUID id, boolean isEnabled)
    {
        return productRepository.existsByProductIdAndIsEnabled(id, isEnabled);
    }

    public Page<Product> getProductsByCategoryId(
            int pageSize,
            int pageNo,
            ProductSortingOptions sortBy,
            SortDirection sortDir,
            UUID categoryId,
            boolean isEnabled)
            throws CategoryNotFoundException
    {

        var pageable = constructPageable(pageNo, pageSize, sortBy, sortDir);
        return productRepository
                .findByCategory_CategoryIdAndIsEnabled(pageable, categoryId, isEnabled);

    }

    public Page<Product> getAllProducts(
            int pageSize,
            int pageNo,
            ProductSortingOptions sortBy,
            SortDirection sortDir,
            boolean isEnabled)
    {
        var pageable = constructPageable(pageSize, pageNo, sortBy, sortDir);
        return productRepository.findAllByIsEnabled(pageable, isEnabled);
    }

    public Product updateProduct(UpdateProductCommand command, boolean isEnabled)
            throws ProductNotFoundException, CategoryNotFoundException
    {
        Product product = productRepository
                .findByProductIdAndIsEnabled(command.productId(), isEnabled)
                .orElseThrow(() -> new ProductNotFoundException(command.productId()));

        if (command.categoryId() != null &&
                !categoryService.categoryExists(command.categoryId(), true)) {
            throw new CategoryNotFoundException(command.categoryId());
        }
        productMapper.updateFromCommand(product, command);
        productRepository.save(product);
        return product;
    }

    public void deactivateProduct(UUID productId, boolean isEnabled)
            throws ProductNotFoundException
    {
        Product product = productRepository.findByProductIdAndIsEnabled(productId, isEnabled)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.setEnabled(false);
        productRepository.save(product);
    }

    public void saveProduct(Product product)
    {
        productRepository.save(product);
    }

    private Pageable constructPageable(
            int pageSize,
            int pageNo,
            ProductSortingOptions sortBy,
            SortDirection dir
    )
    {
        Sort sort;
        sort = Sort.by(Objects.requireNonNullElse(sortBy,
                ProductSortingOptions.createdAt).getPropertyName());

        if (dir == SortDirection.ASC) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }
}

package com.marwan.ecommerce.service.product;

import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.exception.category.CategoryNotFoundException;
import com.marwan.ecommerce.exception.product.ProductNotFoundException;
import com.marwan.ecommerce.mapper.ProductMapper;
import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.service.category.CategoryService;
import com.marwan.ecommerce.service.product.command.CreateProductCommand;
import com.marwan.ecommerce.service.product.command.UpdateProductCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;


    @Transactional
    public Product createProduct(CreateProductCommand command)
            throws CategoryNotFoundException
    {
        Category category = categoryService.getCategory(command.categoryId());
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

    public ProductDetailsDto getActiveProductWithCategoryNameById(UUID id)
            throws ProductNotFoundException
    {
        Product product = productRepository
                .findWithCategoryByProductIdAndIsEnabled(id, true)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (product.getCategory() == null) {
            return productMapper.productToProductDetailsDto(product, null);
        }
        return productMapper.productToProductDetailsDto(product, product.getCategory().getName());
    }

    public ProductDetailsDto getProductWithCategoryNameById(UUID id)
            throws ProductNotFoundException
    {
        Product product = productRepository
                .findWithCategoryByProductId(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (product.getCategory() == null) {
            return productMapper.productToProductDetailsDto(product, null);
        }
        return productMapper.productToProductDetailsDto(product, product.getCategory().getName());
    }

    public Product getActiveProduct(UUID productId)
            throws ProductNotFoundException
    {
        return productRepository
                .findByProductIdAndIsEnabled(productId, true)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }


    public Product getProduct(UUID productId)
            throws ProductNotFoundException
    {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public boolean productExists(UUID id, boolean isEnabled)
    {
        return productRepository.existsByProductIdAndIsEnabled(id, isEnabled);
    }

    public Page<Product> getActiveProductsByCategoryId(
            Pageable pageable,
            UUID categoryId)
            throws CategoryNotFoundException
    {
        if (!categoryService.categoryActive(categoryId)) {
            throw new CategoryNotFoundException(categoryId);
        }
        return productRepository.findByCategory_CategoryIdAndIsEnabled(pageable, categoryId, true);
    }

    public Page<Product> getProductsByCategoryId(
            Pageable pageable,
            UUID categoryId)
            throws CategoryNotFoundException
    {
        return productRepository.findByCategory_CategoryId(pageable, categoryId);
    }

    public Page<Product> getActiveProducts(Pageable pageable)
    {
        return productRepository.findAllByIsEnabled(pageable, true);
    }

    public Page<Product> getAllProducts(Pageable pageable)
    {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public Product updateProduct(UpdateProductCommand command)
            throws ProductNotFoundException, CategoryNotFoundException
    {
        Product product = productRepository
                .findById(command.productId())
                .orElseThrow(() -> new ProductNotFoundException(command.productId()));

        if (command.categoryId() != null &&
                !categoryService.categoryExists(command.categoryId())) {
            throw new CategoryNotFoundException(command.categoryId());
        }
        Category category = categoryService.getCategory(command.categoryId());

        product.setName(command.name());
        product.setDescription(command.description());
        product.setSellingPrice(command.price());
        product.setPictureUrl(command.pictureUrl());
        product.setCategory(category);


        productRepository.save(product);
        return product;
    }

    @Transactional
    public void deactivateProduct(UUID productId)
            throws ProductNotFoundException
    {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.deactivate();
        productRepository.save(product);
    }

    @Transactional
    public void saveProduct(Product product)
    {
        productRepository.save(product);
    }

}

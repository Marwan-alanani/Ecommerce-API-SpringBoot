package com.marwan.ecommerce.service.product;

import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.exception.category.CategoryIdNotFoundException;
import com.marwan.ecommerce.exception.product.ProductIdNotFoundException;
import com.marwan.ecommerce.mapper.ProductMapper;
import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.service.category.CategoryService;
import com.marwan.ecommerce.service.product.command.CreateProductCommand;
import com.marwan.ecommerce.service.product.command.UpdateProductCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
            throws CategoryIdNotFoundException
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
            throws ProductIdNotFoundException
    {
        Product product = productRepository
                .findByProductIdAndIsEnabledWithCategory(id, isEnabled)
                .orElseThrow(() -> new ProductIdNotFoundException(id));

        if (product.getCategory() == null) {
            return productMapper.productToProductDetailsDto(product, null);
        }
        return productMapper.productToProductDetailsDto(product, product.getCategory().getName());

    }

    public Product getProduct(UUID productId, boolean isEnabled)
            throws ProductIdNotFoundException
    {
        Product product = productRepository
                .findByProductIdAndIsEnabled(productId, isEnabled)
                .orElseThrow(() -> new ProductIdNotFoundException(productId));
        return product;
    }

    public boolean productExists(UUID id, boolean isEnabled)
    {
        if (productRepository.existsByProductIdAndIsEnabled(id, isEnabled)) {
            return true;
        }
        return false;
    }

    public List<ProductDetailsDto> getProductsByCategoryId(UUID categoryId, boolean isEnabled)
            throws CategoryIdNotFoundException
    {
        Category category = categoryService.getCategory(categoryId, true);
        List<Product> productList = productRepository
                .findByCategoryIdAndIsEnabled(categoryId, isEnabled);

        List<ProductDetailsDto> productDetailsDtos = new ArrayList<>();
        productList.forEach(product -> {
            productDetailsDtos.add(
                    productMapper.productToProductDetailsDto(
                            product,
                            category.getName())
            );
        });
        return productDetailsDtos;

    }

    public List<Product> getAllProducts(boolean isEnabled)
    {
        return productRepository.findAllByIsEnabled(isEnabled);
    }

    public Product updateProduct(UpdateProductCommand command, boolean isEnabled)
            throws ProductIdNotFoundException, CategoryIdNotFoundException
    {
        Product product = productRepository
                .findByProductIdAndIsEnabled(command.productId(), isEnabled)
                .orElseThrow(() -> new ProductIdNotFoundException(command.productId()));

        if (command.categoryId() != null &&
                !categoryService.categoryExists(command.categoryId(), true)) {
            throw new CategoryIdNotFoundException(command.categoryId());
        }
        productMapper.updateFromCommand(product, command);
        productRepository.save(product);
        return product;
    }

    public void deactivateProduct(UUID productId, boolean isEnabled)
            throws ProductIdNotFoundException
    {
        Product product = productRepository.findByProductIdAndIsEnabled(productId, isEnabled)
                .orElseThrow(() -> new ProductIdNotFoundException(productId));

        product.setEnabled(false);
        productRepository.save(product);
    }
}

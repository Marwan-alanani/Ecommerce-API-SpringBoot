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
        if (command.categoryId() != null && !categoryService.categoryExists(command.categoryId())) {
            throw new CategoryIdNotFoundException(command.categoryId());
        }

        Product product = Product.create(
                command.name(),
                command.description(),
                command.price(),
                command.pictureUrl(),
                command.categoryId()
        );
        productRepository.save(product);
        return product;
    }

    public ProductDetailsDto getProductWithCategoryNameById(UUID id)
            throws ProductIdNotFoundException
    {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductIdNotFoundException(id);
        }
        Product product = optionalProduct.get();
        if (product.getCategoryId() == null) {
            return productMapper.productToProductDetailsDto(product, null);
        }
        String categoryName = categoryService.getCategory(product.getCategoryId()).getName();
        return productMapper.productToProductDetailsDto(product, categoryName);

    }

    public Product getProduct(UUID productId)
            throws ProductIdNotFoundException
    {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductIdNotFoundException(productId);
        }
        return optionalProduct.get();
    }

    public boolean productExists(UUID id)
    {
        if (productRepository.existsById(id)) {
            return true;
        }
        return false;
    }

    public List<ProductDetailsDto> getProductsByCategoryId(UUID categoryId)
            throws CategoryIdNotFoundException
    {
        Category category = categoryService.getCategory(categoryId);
        List<Product> productList = productRepository.findByCategoryId(categoryId);
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

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    public Product updateProduct(UpdateProductCommand command)
            throws ProductIdNotFoundException, CategoryIdNotFoundException
    {
        Optional<Product> optionalProduct = productRepository.findById(command.productId());
        if (optionalProduct.isEmpty()) {
            throw new ProductIdNotFoundException(command.productId());
        }
        Product product = optionalProduct.get();
        if (command.categoryId() != null && !categoryService.categoryExists(command.categoryId())) {
            throw new CategoryIdNotFoundException(command.categoryId());
        }
        productMapper.updateFromCommand(product, command);
        productRepository.save(product);
        return product;
    }

    public void deleteProduct(UUID id)
            throws ProductIdNotFoundException
    {
        if (!productRepository.existsById(id)) {
            throw new ProductIdNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}

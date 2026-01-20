package com.marwan.ecommerce.service.product;

import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.mapper.ProductMapper;
import com.marwan.ecommerce.model.product.entity.Product;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.service.category.CategoryService;
import com.marwan.ecommerce.service.product.command.CreateProductCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService
{
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductDetailsDto createProduct(CreateProductCommand command)
    {
        Product product = Product.create(
                command.name(),
                command.description(),
                command.price(),
                command.pictureUrl(),
                command.categoryId()
        );
        productRepository.save(product);
        return ProductMapper.mapProductToProductDetailsDto(product);
    }

    public List<Product> getCategoryProducts(UUID categoryId) throws Exception
    {
        if (!categoryService.categoryExists(categoryId)) {
            throw new Exception("doesn't exist");
        }
        return productRepository.findByCategoryId(categoryId);
    }
}

package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Category;
import com.marwan.ecommerce.model.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
@Testcontainers
public class ProductRepositoryTests
{
    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry r)
    {
        r.add("spring.datasource.url", postgres::getJdbcUrl);
        r.add("spring.datasource.username", postgres::getUsername);
        r.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void ProductRepository_SaveAll_ReturnsSavedProduct()
    {
        // Arrange
        Category category = Category.create(
                "Furniture"
        );
        categoryRepository.save(category);

        Product product = Product.create(
                "Bed",
                "",
                BigDecimal.valueOf(10.21),
                "sdfsdfdsf",
                category
        );

        // Act
        var newProduct = productRepository.save(product);
        // Assert
        Assertions.assertNotNull(newProduct);
        Assertions.assertNotNull(newProduct.getProductId());
        Assertions.assertEquals(newProduct.getProductId(), product.getProductId());
        Assertions.assertEquals(newProduct.getName(), product.getName());
    }

    @Test
    public void ProductRepository_FindById_ReturnsProduct()
    {
        // Arrange
        Category category = Category.create(
                "Furniture"
        );
        categoryRepository.save(category);

        Product product = Product.create(
                "Bed",
                "",
                BigDecimal.valueOf(10.21),
                "sdfsdfdsf",
                category
        );
        var productId = product.getProductId();
        // Act
        var savedProduct = productRepository.save(product);
        var retrievedProduct = productRepository.findById(productId).orElse(null);
        // assert
        Assertions.assertNotNull(retrievedProduct);
        Assertions.assertEquals(savedProduct.getProductId(), productId);
        Assertions.assertEquals(savedProduct.getName(), product.getName());
        Assertions.assertEquals(savedProduct.getProductId(), retrievedProduct.getProductId());
    }

    @Test
    public void ProductRepository_FindAll_ReturnsAllProducts()
    {
        Category category = Category.create(
                "Furniture"
        );
        categoryRepository.save(category);
        Product product = Product.create(
                "Bed",
                "",
                BigDecimal.valueOf(10.21),
                "sdfsdfdsf",
                category
        );

        Product product2 = Product.create(
                "Table",
                "",
                BigDecimal.valueOf(10.21),
                "sdfsdfdsf",
                category
        );
        productRepository.save(product);
        productRepository.save(product2);
        List<Product> allProducts = productRepository.findAll();
        Assertions.assertNotNull(allProducts);
        Assertions.assertFalse(allProducts.isEmpty());
        Assertions.assertEquals(2, allProducts.size());
        Assertions.assertEquals(product.getProductId(), allProducts.get(0).getProductId());
        Assertions.assertEquals(product2.getProductId(), allProducts.get(1).getProductId());
    }
}
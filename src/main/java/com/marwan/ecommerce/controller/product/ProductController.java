package com.marwan.ecommerce.controller.product;

import com.marwan.ecommerce.controller.product.request.CreateProductRequest;
import com.marwan.ecommerce.controller.product.request.UpdateProductRequest;
import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.dto.product.ProductResponseDto;
import com.marwan.ecommerce.exception.category.CategoryIdNotFoundException;
import com.marwan.ecommerce.exception.product.ProductIdNotFoundException;
import com.marwan.ecommerce.mapper.ProductMapper;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.service.product.ProductService;
import com.marwan.ecommerce.service.product.command.CreateProductCommand;
import com.marwan.ecommerce.service.product.command.UpdateProductCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController
{
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody CreateProductRequest request)
            throws CategoryIdNotFoundException
    {
        CreateProductCommand command =
                productMapper.createProductRequestToCreateProductCommand(request);
        Product product = productService.createProduct(command);
        ProductResponseDto productResponseDto = productMapper.productToProductResponseDto(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailsDto> getProduct(@PathVariable UUID productId)
            throws ProductIdNotFoundException
    {
        ProductDetailsDto productDetailsDto =
                productService.getProductWithCategoryNameById(productId, true);
        return ResponseEntity.ok(productDetailsDto);
    }

    @PostMapping("/update")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @RequestBody UpdateProductRequest request)
            throws ProductIdNotFoundException, CategoryIdNotFoundException
    {
        UpdateProductCommand command =
                productMapper.updateProductRequestToUpdateProductCommand(request);

        Product product = productService.updateProduct(command, true);
        ProductResponseDto productResponseDto = productMapper.productToProductResponseDto(product);
        return ResponseEntity.ok(productResponseDto);
    }

    @PostMapping("/delete/{productId}")
    public ResponseEntity<?> deactivateProduct(@PathVariable UUID productId)
            throws ProductIdNotFoundException
    {
        productService.deactivateProduct(productId, true);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts()
    {
        List<Product> products = productService.getAllProducts(true);
        List<ProductResponseDto> productResponseDtos =
                productMapper.productListToProductResponseDtoList(products);
        return ResponseEntity.ok(productResponseDtos);
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDetailsDto>> getCategoryProducts(
            @PathVariable UUID categoryId)
            throws CategoryIdNotFoundException
    {
        List<ProductDetailsDto> productDetailsDtos =
                productService.getProductsByCategoryId(categoryId, true);
        return ResponseEntity.status(HttpStatus.OK).body(productDetailsDtos);
    }

}

package com.marwan.ecommerce.controller.product;

import com.marwan.ecommerce.controller.product.request.CreateProductRequest;
import com.marwan.ecommerce.controller.product.request.UpdateProductRequest;
import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.dto.product.ProductResponseDto;
import com.marwan.ecommerce.exception.category.CategoryNotFoundException;
import com.marwan.ecommerce.exception.product.ProductNotFoundException;
import com.marwan.ecommerce.mapper.ProductMapper;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.service.product.ProductService;
import com.marwan.ecommerce.service.product.command.CreateProductCommand;
import com.marwan.ecommerce.service.product.command.UpdateProductCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
            @Valid @RequestBody CreateProductRequest request,
            UriComponentsBuilder uriBuilder)
            throws CategoryNotFoundException
    {
        CreateProductCommand command =
                productMapper.createProductRequestToCreateProductCommand(request);
        Product product = productService.createProduct(command);
        ProductResponseDto productResponseDto = productMapper.productToProductResponseDto(product);
        var uri =
                uriBuilder.path("/products/{productId}")
                        .buildAndExpand(product.getProductId()).toUri();
        return ResponseEntity.created(uri).body(productResponseDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailsDto> getProduct(@PathVariable UUID productId)
            throws ProductNotFoundException
    {
        ProductDetailsDto productDetailsDto =
                productService.getProductWithCategoryNameById(productId, true);
        return ResponseEntity.ok(productDetailsDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @Valid @RequestBody UpdateProductRequest request)
            throws ProductNotFoundException, CategoryNotFoundException
    {
        UpdateProductCommand command =
                productMapper.updateProductRequestToUpdateProductCommand(request);

        Product product = productService.updateProduct(command, true);
        ProductResponseDto productResponseDto = productMapper.productToProductResponseDto(product);
        return ResponseEntity.ok(productResponseDto);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deactivateProduct(@PathVariable UUID productId)
            throws ProductNotFoundException
    {
        productService.deactivateProduct(productId, true);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(
            @RequestParam(required = false) UUID categoryId
    )
            throws CategoryNotFoundException
    {
        List<Product> products;
        if (categoryId == null) {
            products = productService.getAllProducts(true);
        }
        else{
            products = productService.getProductsByCategoryId(categoryId, true);
        }
        List<ProductResponseDto> productResponseDtos =
                productMapper.productListToProductResponseDtoList(products);
        return ResponseEntity.ok(productResponseDtos);
    }


}

package com.marwan.ecommerce.controller.product;

import com.marwan.ecommerce.controller.product.request.CreateProductRequest;
import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.dto.product.ProductPagingOptions;
import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.dto.product.ProductResponseDto;
import com.marwan.ecommerce.exception.category.CategoryNotFoundException;
import com.marwan.ecommerce.exception.product.ProductNotFoundException;
import com.marwan.ecommerce.mapper.ProductMapper;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.service.product.ProductService;
import com.marwan.ecommerce.service.product.command.CreateProductCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

import static com.marwan.ecommerce.controller.common.BaseController.toPageDto;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController
{
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> createProduct(
            @Valid @RequestBody CreateProductRequest request,
            UriComponentsBuilder uriBuilder)
            throws CategoryNotFoundException
    {
        CreateProductCommand command = productMapper.createProductRequestToCommand(request);
        Product product = productService.createProduct(command);
        ProductResponseDto productResponseDto = productMapper.productToProductResponseDto(product);
        var uri = uriBuilder
                .path("/products/{productId}")
                .buildAndExpand(product.getProductId())
                .toUri();
        return ResponseEntity.created(uri).body(productResponseDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailsDto> getProduct(@PathVariable UUID productId)
            throws ProductNotFoundException
    {
        ProductDetailsDto productDetailsDto =
                productService.getActiveProductWithCategoryNameById(productId);
        return ResponseEntity.ok(productDetailsDto);
    }

    @GetMapping
    public ResponseEntity<PageDto<ProductResponseDto>> getAllProducts(
            @Valid ProductPagingOptions pagingOptions,
            @RequestParam(required = false) UUID categoryId
    )
            throws CategoryNotFoundException
    {
        Page<Product> productPage;
        if (categoryId != null) {
            productPage = productService.getActiveProductsByCategoryId(pagingOptions, categoryId);
        } else {
            productPage = productService.getActiveProducts(pagingOptions);
        }
        List<ProductResponseDto> productResponseDtos =
                productMapper.productListToProductResponseDtoList(productPage.getContent());

        return ResponseEntity.ok(toPageDto(productPage, productResponseDtos));
    }
}

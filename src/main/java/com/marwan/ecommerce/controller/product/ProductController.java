package com.marwan.ecommerce.controller.product;

import com.marwan.ecommerce.controller.product.request.CreateProductRequest;
import com.marwan.ecommerce.controller.product.request.UpdateProductRequest;
import com.marwan.ecommerce.dto.common.PageDto;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    @PreAuthorize("hasAnyRole('ADMIN')")
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

    @GetMapping("/active/{productId}")
    public ResponseEntity<ProductDetailsDto> getActiveProduct(@PathVariable UUID productId)
            throws ProductNotFoundException
    {
        ProductDetailsDto productDetailsDto =
                productService.getActiveProductWithCategoryNameById(productId);
        return ResponseEntity.ok(productDetailsDto);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ProductDetailsDto> getProduct(@PathVariable UUID productId)
            throws ProductNotFoundException
    {
        ProductDetailsDto productDetailsDto = productService
                .getProductWithCategoryNameById(productId);
        return ResponseEntity.ok(productDetailsDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PageDto<ProductResponseDto>> getAllProducts(
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction =
                    Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) UUID categoryId
    )
            throws CategoryNotFoundException
    {

        Page<Product> productPage;
        if (categoryId != null) {
            productPage = productService.getProductsByCategoryId(
                    pageable,
                    categoryId
            );
        } else
            productPage = productService.getAllProducts(pageable);

        List<ProductResponseDto> productResponseDtos =
                productMapper.productListToProductResponseDtoList(productPage.getContent());

        return ResponseEntity.ok(toPageDto(productPage, productResponseDtos));
    }

    @GetMapping("/active")
    public ResponseEntity<PageDto<ProductResponseDto>> getActiveProducts(
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) UUID categoryId
    )
            throws CategoryNotFoundException
    {
        Page<Product> productPage;
        if (categoryId != null) {
            productPage = productService.getActiveProductsByCategoryId(pageable, categoryId);
        } else {
            productPage = productService.getActiveProducts(pageable);
        }
        List<ProductResponseDto> productResponseDtos =
                productMapper.productListToProductResponseDtoList(productPage.getContent());

        return ResponseEntity.ok(toPageDto(productPage, productResponseDtos));
    }

    @PatchMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @Valid @RequestBody UpdateProductRequest request)
            throws ProductNotFoundException, CategoryNotFoundException
    {
        UpdateProductCommand command = productMapper.updateProductRequestToCommand(request);
        Product product = productService.updateProduct(command);
        ProductResponseDto productResponseDto = productMapper.productToProductResponseDto(product);
        return ResponseEntity.ok(productResponseDto);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deactivateProduct(@PathVariable UUID productId)
            throws ProductNotFoundException
    {
        productService.deactivateProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
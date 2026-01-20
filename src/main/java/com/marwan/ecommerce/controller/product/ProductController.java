package com.marwan.ecommerce.controller.product;

import com.marwan.ecommerce.controller.product.request.CreateProductRequest;
import com.marwan.ecommerce.dto.product.ProductDetailsDto;
import com.marwan.ecommerce.mapper.ProductMapper;
import com.marwan.ecommerce.model.product.entity.Product;
import com.marwan.ecommerce.service.product.ProductService;
import com.marwan.ecommerce.service.product.command.CreateProductCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController
{
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDetailsDto> CreateProduct(
            @RequestBody CreateProductRequest request)
    {
        CreateProductCommand command =
                ProductMapper.mapCreateProductRequestToCreateProductCommand(request);
        ProductDetailsDto productDetailsDto = productService.createProduct(command);
        return ResponseEntity.ok(productDetailsDto);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDetailsDto>> getCategoryProducts(
            @PathVariable UUID categoryId)
            throws Exception
    {
        List<Product> productList = productService.getCategoryProducts(categoryId);
        List<ProductDetailsDto> productDetailsDtos = new ArrayList<>();
        productList.forEach(product -> {
            productDetailsDtos.add(ProductMapper.mapProductToProductDetailsDto(product));
        });
        return ResponseEntity.status(HttpStatus.OK).body(productDetailsDtos);
    }

}

package com.marwan.ecommerce.controller.purchase;

import com.marwan.ecommerce.controller.common.converter.BaseController;
import com.marwan.ecommerce.controller.purchase.request.CreatePurchaseRequest;
import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.dto.purchase.PurchaseDto;
import com.marwan.ecommerce.dto.purchase.PurchasePagingOptions;
import com.marwan.ecommerce.exception.product.ProductNotFoundException;
import com.marwan.ecommerce.exception.purchase.PurchaseNotFoundException;
import com.marwan.ecommerce.exception.supplier.SupplierNotFoundException;
import com.marwan.ecommerce.mapper.PurchaseMapper;
import com.marwan.ecommerce.model.entity.Purchase;
import com.marwan.ecommerce.service.purchase.PurchaseService;
import com.marwan.ecommerce.service.purchase.command.CreatePurchaseCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/purchases")
@RequiredArgsConstructor
@RestController
public class PurchaseController extends BaseController
{
    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;

    @PostMapping("/create")
    public ResponseEntity<PurchaseDto> create(@Valid @RequestBody CreatePurchaseRequest request)
            throws SupplierNotFoundException, ProductNotFoundException
    {
        CreatePurchaseCommand command =
                purchaseMapper.createPurchaseRequestToCreatePurchaseCommand(request);
        Purchase purchase = purchaseService.createPurchase(command);
        PurchaseDto purchaseDto = purchaseMapper.purchaseToPurchaseDto(purchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseDto);
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<PurchaseDto> get(@PathVariable UUID purchaseId)
            throws PurchaseNotFoundException
    {
        Purchase purchase = purchaseService.getById(purchaseId);
        PurchaseDto purchaseDto = purchaseMapper.purchaseToPurchaseDto(purchase);
        return ResponseEntity.ok(purchaseDto);

    }

    @GetMapping
    public ResponseEntity<PageDto<PurchaseDto>> getAll(
            @Valid PurchasePagingOptions pagingOptions,
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) UUID supplierId
    )
    {
        Page<Purchase> purchasePage;
        if (productId == null && supplierId == null) {
            purchasePage = purchaseService.getAll(pagingOptions);
        } else if (productId != null && supplierId != null) {
            purchasePage = purchaseService.getAllBySupplierIdAndProductId(
                    pagingOptions,
                    supplierId,
                    productId);
        } else if (supplierId != null) {
            purchasePage = purchaseService.getAllBySupplierId(
                    pagingOptions,
                    supplierId);
        } else {
            purchasePage = purchaseService.getAllByProductId(
                    pagingOptions,
                    productId);
        }

        List<PurchaseDto> purchaseDtos = purchaseMapper
                .purchaseListToPurchaseDtoList(purchasePage.getContent());

        return ResponseEntity.ok(toPageDto(purchasePage, purchaseDtos));
    }

}

package com.marwan.ecommerce.controller.purchase;

import com.marwan.ecommerce.controller.purchase.request.CreatePurchaseRequest;
import com.marwan.ecommerce.dto.purchase.PurchaseDto;
import com.marwan.ecommerce.exception.product.ProductNotFoundException;
import com.marwan.ecommerce.exception.purchase.PurchaseNotFoundException;
import com.marwan.ecommerce.exception.supplier.SupplierNotFoundException;
import com.marwan.ecommerce.mapper.PurchaseMapper;
import com.marwan.ecommerce.model.entity.Purchase;
import com.marwan.ecommerce.service.purchase.PurchaseService;
import com.marwan.ecommerce.service.purchase.command.CreatePurchaseCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController
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
    public ResponseEntity<List<PurchaseDto>> getAll(
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) UUID supplierId
    )
    {
        List<Purchase> purchaseList;
        if (productId == null && supplierId == null) {
            purchaseList = purchaseService.getAll();
        } else if (productId != null && supplierId != null) {
            purchaseList = purchaseService.getAllBySupplierIdAndProductId(supplierId, productId);
        } else if (supplierId != null) {
            purchaseList = purchaseService.getAllBySupplierId(supplierId);
        } else {
            purchaseList = purchaseService.getAllByProductId(productId);
        }
        return ResponseEntity.ok(purchaseMapper.purchaseListToPurchaseDtoList(purchaseList));
    }

}

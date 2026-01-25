package com.marwan.ecommerce.controller.purchase;

import com.marwan.ecommerce.controller.purchase.request.CreatePurchaseRequest;
import com.marwan.ecommerce.dto.purchase.PurchaseDto;
import com.marwan.ecommerce.exception.product.ProductIdNotFoundException;
import com.marwan.ecommerce.exception.purchase.PurchaseIdNotFoundException;
import com.marwan.ecommerce.exception.supplier.SupplierIdNotFoundException;
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
            throws SupplierIdNotFoundException, ProductIdNotFoundException
    {
        CreatePurchaseCommand command =
                purchaseMapper.createPurchaseRequestToCreatePurchaseCommand(request);
        Purchase purchase = purchaseService.create(command);
        PurchaseDto purchaseDto = purchaseMapper.purchaseToPurchaseDto(purchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseDto);
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<PurchaseDto> get(@PathVariable UUID purchaseId)
            throws PurchaseIdNotFoundException
    {
        Purchase purchase = purchaseService.getById(purchaseId);
        PurchaseDto purchaseDto = purchaseMapper.purchaseToPurchaseDto(purchase);
        return ResponseEntity.ok(purchaseDto);

    }

    @GetMapping("/all")
    public ResponseEntity<List<PurchaseDto>> getAll()
    {
        List<Purchase> purchaseList = purchaseService.getAll();
        List<PurchaseDto> purchaseDtoList = purchaseMapper
                .purchaseListToPurchaseDtoList(purchaseList);
        return ResponseEntity.ok(purchaseDtoList);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseDto>> getAllByProductId(@RequestParam UUID productId)
    {
        List<Purchase> purchaseList = purchaseService.getAllByProductId(productId);
        List<PurchaseDto> purchaseDtoList = purchaseMapper
                .purchaseListToPurchaseDtoList(purchaseList);
        return ResponseEntity.ok(purchaseDtoList);

    }


    public ResponseEntity<List<PurchaseDto>> getAllBySupplierId(@RequestParam UUID supplierId)
    {
        List<Purchase> purchaseList = purchaseService.getAllBySupplierId(supplierId);
        List<PurchaseDto> purchaseDtoList = purchaseMapper
                .purchaseListToPurchaseDtoList(purchaseList);
        return ResponseEntity.ok(purchaseDtoList);
    }


    public ResponseEntity<List<PurchaseDto>> getAllByProductAndSupplierId(
            @RequestParam UUID supplierId,
            @RequestParam UUID productId
    )
    {
        List<Purchase> purchaseList = purchaseService
                .getAllBySupplierIdAndProductId(supplierId, productId);

        List<PurchaseDto> purchaseDtoList = purchaseMapper
                .purchaseListToPurchaseDtoList(purchaseList);
        return ResponseEntity.ok(purchaseDtoList);
    }
}

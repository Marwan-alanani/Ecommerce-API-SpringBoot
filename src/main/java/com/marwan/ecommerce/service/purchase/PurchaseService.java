package com.marwan.ecommerce.service.purchase;

import com.marwan.ecommerce.exception.product.ProductNotFoundException;
import com.marwan.ecommerce.exception.purchase.PurchaseNotFoundException;
import com.marwan.ecommerce.exception.supplier.SupplierNotFoundException;
import com.marwan.ecommerce.model.entity.Purchase;
import com.marwan.ecommerce.repository.PurchaseRepository;
import com.marwan.ecommerce.service.product.ProductService;
import com.marwan.ecommerce.service.purchase.command.CreatePurchaseCommand;
import com.marwan.ecommerce.service.supplier.SupplierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PurchaseService
{
    private final PurchaseRepository purchaseRepository;
    private final ProductService productService;
    private final SupplierService supplierService;

    @Transactional
    public Purchase createPurchase(CreatePurchaseCommand command)
            throws ProductNotFoundException, SupplierNotFoundException
    {
        if (!productService.productExists(command.productId(), true)) {
            throw new ProductNotFoundException(command.productId());
        }
        if (!supplierService.supplierExistsAndEnabled(command.supplierId(), true)) {
            throw new SupplierNotFoundException(command.supplierId());
        }
        Purchase purchase = Purchase.create(
                command.productId(),
                command.unitPrice(),
                command.quantity(),
                command.supplierId()
        );

        // event published on save by default
        return purchaseRepository.save(purchase);
    }

    public Purchase getById(UUID purchaseId)
            throws PurchaseNotFoundException
    {
        Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);
        if (purchase.isEmpty()) {
            throw new PurchaseNotFoundException(purchaseId);
        }
        return purchase.get();
    }

    public Page<Purchase> getAll(Pageable pageable)
    {
        return purchaseRepository.findAll(pageable);
    }

    public Page<Purchase> getAllByProductId(
            Pageable pageable,
            UUID productId)
    {
        return purchaseRepository.findByProductId(pageable, productId);
    }

    public Page<Purchase> getAllBySupplierId(Pageable pageable, UUID supplierId)
    {
        return purchaseRepository.findBySupplierId(pageable, supplierId);
    }

    public Page<Purchase> getAllBySupplierIdAndProductId(Pageable pageable,
            UUID supplierId,
            UUID productId)
    {

        return purchaseRepository.findBySupplierIdAndProductId(pageable, supplierId, productId);
    }


}

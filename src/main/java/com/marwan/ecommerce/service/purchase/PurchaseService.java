package com.marwan.ecommerce.service.purchase;

import com.marwan.ecommerce.exception.product.ProductNotFoundException;
import com.marwan.ecommerce.exception.purchase.PurchaseNotFoundException;
import com.marwan.ecommerce.exception.supplier.SupplierNotFoundException;
import com.marwan.ecommerce.model.entity.Purchase;
import com.marwan.ecommerce.repository.PurchaseRepository;
import com.marwan.ecommerce.service.product.ProductService;
import com.marwan.ecommerce.service.purchase.command.CreatePurchaseCommand;
import com.marwan.ecommerce.service.purchase.event.purchaseCreated.PurchaseCreatedEvent;
import com.marwan.ecommerce.service.supplier.SupplierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseService
{
    private final PurchaseRepository purchaseRepository;
    private final ProductService productService;
    private final SupplierService supplierService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Purchase createPurchase(CreatePurchaseCommand command)
            throws ProductNotFoundException, SupplierNotFoundException
    {
        if (!productService.productExists(command.productId(), true)) {
            throw new ProductNotFoundException(command.productId());
        }
        if (!supplierService.supplierExists(command.supplierId(), true)) {
            throw new SupplierNotFoundException(command.supplierId());
        }
        Purchase purchase = Purchase.create(
                command.productId(),
                command.unitPrice(),
                command.quantity(),
                command.supplierId()
        );

        purchaseRepository.save(purchase);
        // raise an event here
        applicationEventPublisher.publishEvent(
                new PurchaseCreatedEvent(
                        purchase.getProductId(),
                        purchase.getUnitPrice(),
                        purchase.getQuantity()
                ));
        return purchase;
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

    public List<Purchase> getAll()
    {
        return purchaseRepository.findAll();
    }

    public List<Purchase> getAllByProductId(UUID productId)
    {
        return purchaseRepository.findByProductId(productId);
    }

    public List<Purchase> getAllBySupplierId(UUID supplierId)
    {
        return purchaseRepository.findBySupplierId(supplierId);
    }

    public List<Purchase> getAllBySupplierIdAndProductId(UUID supplierId, UUID productId)
    {
        return purchaseRepository.findBySupplierIdAndProductId(supplierId, productId);
    }


}

package com.marwan.ecommerce.service.purchase;

import com.marwan.ecommerce.exception.product.ProductIdNotFoundException;
import com.marwan.ecommerce.exception.purchase.PurchaseIdNotFoundException;
import com.marwan.ecommerce.exception.supplier.SupplierIdNotFoundException;
import com.marwan.ecommerce.model.entity.Purchase;
import com.marwan.ecommerce.repository.PurchaseRepository;
import com.marwan.ecommerce.service.product.ProductService;
import com.marwan.ecommerce.service.purchase.command.CreatePurchaseCommand;
import com.marwan.ecommerce.service.purchase.event.PurchaseCreatedEvent;
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

    public Purchase create(CreatePurchaseCommand command)
            throws ProductIdNotFoundException, SupplierIdNotFoundException
    {
        if (!productService.productExists(command.productId())) {
            throw new ProductIdNotFoundException(command.productId());
        }
        if (!supplierService.supplierExists(command.supplierId())) {
            throw new SupplierIdNotFoundException(command.supplierId());
        }
        Purchase purchase = Purchase.create(
                command.productId(),
                command.price(),
                command.quantity(),
                command.supplierId()
        );

        purchaseRepository.save(purchase);
        // raise an event here
        applicationEventPublisher.publishEvent(
                new PurchaseCreatedEvent(
                        purchase.getProductId(),
                        purchase.getPrice()
                ));
        return purchase;
    }

    public Purchase getById(UUID purchaseId)
            throws PurchaseIdNotFoundException
    {
        Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);
        if (purchase.isEmpty()) {
            throw new PurchaseIdNotFoundException(purchaseId);
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

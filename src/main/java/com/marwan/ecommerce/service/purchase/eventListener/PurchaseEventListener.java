package com.marwan.ecommerce.service.purchase.eventListener;

import com.marwan.ecommerce.exception.product.ProductIdNotFoundException;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.service.purchase.event.PurchaseCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PurchaseEventListener
{
    private final ProductRepository productRepository;

    @EventListener
    public void onPurchaseCreated(PurchaseCreatedEvent event)
            throws ProductIdNotFoundException
    {
        Product product = productRepository.getReferenceById(event.productId());
        if (product.getMaxPurchasePrice() < event.price()) {
            if (product.getPrice() < event.price()) {
                product.setPrice(event.price() * 1.2);
            }
            product.setMaxPurchasePrice(event.price());
            productRepository.save(product);
        }
    }
}


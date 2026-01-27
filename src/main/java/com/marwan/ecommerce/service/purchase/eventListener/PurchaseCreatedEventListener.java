package com.marwan.ecommerce.service.purchase.eventListener;

import com.marwan.ecommerce.exception.product.ProductNotFoundException;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.service.purchase.event.PurchaseCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PurchaseCreatedEventListener
{
    private final ProductRepository productRepository;

    @EventListener
    public void onPurchaseCreated(PurchaseCreatedEvent event)
            throws ProductNotFoundException
    {
        Product product = productRepository.findById(event.productId())
                .orElseThrow(() -> new ProductNotFoundException(event.productId()));

        BigDecimal totalPrice = product.getTotalPurchasePrice()
                .add(event.unitPrice().multiply(BigDecimal.valueOf(event.quantity())));

        long totalQuantity = product.getTotalPurchaseQuantity() + event.quantity();

        product.setTotalPurchasePrice(totalPrice);
        product.setTotalPurchaseQuantity(totalQuantity);

        BigDecimal averagePrice = totalPrice.divide(
                BigDecimal.valueOf(totalQuantity), 3, BigDecimal.ROUND_HALF_UP
        );
        product.setSellingPrice(averagePrice);
        product.increaseBalance(event.quantity());
        productRepository.save(product);
    }
}


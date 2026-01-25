package com.marwan.ecommerce.service.purchase.eventListener;

import com.marwan.ecommerce.exception.product.ProductIdNotFoundException;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.repository.ProductRepository;
import com.marwan.ecommerce.service.purchase.event.PurchaseCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PurchaseEventListener
{
    private final ProductRepository productRepository;

    @EventListener
    public void onPurchaseCreated(PurchaseCreatedEvent event)
            throws ProductIdNotFoundException
    {
        Product product = productRepository.findById(event.productId())
                .orElseThrow(() -> new ProductIdNotFoundException(event.productId()));

        BigDecimal totalPrice = product.getTotalPurchasePrice()
                .add(BigDecimal.valueOf(
                        event.price() * Double.valueOf(event.quantity()))
                );

        long totalQuantity = product.getTotalPurchaseQuantity() + event.quantity();

        product.setTotalPurchasePrice(totalPrice);
        product.setTotalPurchaseQuantity(totalQuantity);

        BigDecimal averagePrice = totalPrice.divide(
                BigDecimal.valueOf(totalQuantity)
        );

        product.setPrice(averagePrice.doubleValue());
        productRepository.save(product);
    }
}


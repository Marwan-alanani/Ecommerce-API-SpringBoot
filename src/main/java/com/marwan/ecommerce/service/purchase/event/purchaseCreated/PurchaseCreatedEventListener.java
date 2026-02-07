package com.marwan.ecommerce.service.purchase.event.purchaseCreated;

import com.marwan.ecommerce.exception.product.ProductNotFoundException;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class PurchaseCreatedEventListener
{
    private final ProductRepository productRepository;


    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onPurchaseCreated(PurchaseCreatedEvent event)
    {

        int maxAttempts = 5;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                Product product = productRepository.findById(event.productId())
                        .orElseThrow(() -> new ProductNotFoundException(event.productId()));

                BigDecimal totalPrice = product.getTotalPurchasePrice()
                        .add(event.unitPrice().multiply(BigDecimal.valueOf(event.quantity())));

                long totalQuantity = product.getTotalPurchaseQuantity() + event.quantity();

                product.setTotalPurchasePrice(totalPrice);
                product.setTotalPurchaseQuantity(totalQuantity);

                BigDecimal averagePrice = totalPrice.divide(
                        BigDecimal.valueOf(totalQuantity), 3, RoundingMode.HALF_UP
                );

                product.setSellingPrice(averagePrice);
                product.increaseBalance(event.quantity());

                productRepository.saveAndFlush(product); // flush to trigger version check now
                return;

            } catch (ObjectOptimisticLockingFailureException |
                     jakarta.persistence.OptimisticLockException ex) {
                if (attempt == maxAttempts) {
                    throw ex; // or wrap in your own exception
                }

                // exponential backoff: 10, 20, 40, 80...
                try {
                    long sleepMs = Math.min(10L * (1L << (attempt - 1)), 200L);
                    Thread.sleep(sleepMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", ie);
                }
            }
        }
    }
}
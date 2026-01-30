package com.marwan.ecommerce.service.order.event.orderPaid;

import com.marwan.ecommerce.exception.order.OrderNotFoundException;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.model.entity.OrderItem;
import com.marwan.ecommerce.model.entity.Product;
import com.marwan.ecommerce.repository.OrderRepository;
import com.marwan.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderPaidEventListener
{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleOrderPaidEvent(OrderPaidEvent event)
    {
        Order order = orderRepository.findByOrderIdWithOrderItems(event.orderId())
                .orElseThrow(() -> new OrderNotFoundException(event.orderId()));

        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = productRepository.findById(orderItem.getProductId())
                    .orElse(null);
            if (product == null)
                // maybe throwing an error would've been better idk
                continue;

            product.decreaseBalance(orderItem.getQuantity());
            productRepository.save(product);

        }

    }

}
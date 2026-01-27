package com.marwan.ecommerce.service.checkout;

import com.marwan.ecommerce.exception.cart.CartWithUserIdNotFoundException;
import com.marwan.ecommerce.model.entity.Cart;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.repository.CartRepository;
import com.marwan.ecommerce.repository.OrderRepository;
import com.marwan.ecommerce.service.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckoutService
{
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public UUID checkout(UUID userId)
    {
        Cart cart = cartRepository.getCartWithItemsByUserId(userId)
                .orElseThrow(() -> new CartWithUserIdNotFoundException(userId));

        Order newOrder = Order.fromCart(cart);
        cart.clear();

        orderRepository.save(newOrder);
        cartRepository.save(cart);

        return newOrder.getOrderId();
    }
}

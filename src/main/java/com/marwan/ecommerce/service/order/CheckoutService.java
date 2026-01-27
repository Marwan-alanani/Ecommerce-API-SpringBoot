package com.marwan.ecommerce.service.order;

import com.marwan.ecommerce.dto.order.CheckoutResponseDto;
import com.marwan.ecommerce.exception.cart.CartEmptyException;
import com.marwan.ecommerce.exception.order.PaymentException;
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
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;


    public CheckoutResponseDto checkout(UUID userId)
    {
        Cart cart = cartService.getCartWithUserId(userId);

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        Order order = Order.fromCart(cart);
        orderRepository.save(order);

        try {
            var session = paymentGateway.createCheckoutSession(order);
            cart.clear();
            cartRepository.save(cart);
            return new CheckoutResponseDto(order.getOrderId(), session.checkoutUrl());
        } catch (PaymentException ex) {
            orderRepository.delete(order);
            throw ex;
        }
    }
}

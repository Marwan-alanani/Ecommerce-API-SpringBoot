package com.marwan.ecommerce.service.payment;

import com.marwan.ecommerce.exception.cart.CartEmptyException;
import com.marwan.ecommerce.exception.product.NotEnoughProductException;
import com.marwan.ecommerce.model.entity.Cart;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.model.entity.Payment;
import com.marwan.ecommerce.model.enums.PaymentProvider;
import com.marwan.ecommerce.service.cart.CartService;
import com.marwan.ecommerce.service.order.OrderService;
import com.marwan.ecommerce.service.order.command.LineItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutServiceTx
{
    private final CartService cartService;
    private final OrderService orderService;
    private final PaymentService paymentService;


    @Transactional
    public void finalizeCheckout(UUID paymentId, PaymentProvider paymentProvider, UUID cartId,
            UUID orderId, String sessionId)
    {
        Payment payment = paymentService.getPayment(paymentId);
        Cart cart = cartService.getCart(cartId);
        Order order = orderService.getOrder(orderId);

        payment.addPaymentProvider(paymentProvider);
        payment.setCheckoutSessionId(sessionId);
        cart.clear();
        order.markPaymentPending();
        orderService.save(order);
        cartService.saveCart(cart);
        paymentService.save(payment);
    }

    @Transactional
    public CheckoutInit initiateCheckout(UUID userId)
    {
        // returns order,payment,cart,and LineItemDtoList

        Cart cart = cartService.getCartWithUserId(userId);
        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }
        for (var item : cart.getCartItems()) {
            if (item.getQuantity() > item.getProduct().getBalance()) {
                throw new NotEnoughProductException(
                        item.getProduct().getName(),
                        item.getProduct().getBalance(),
                        item.getQuantity()
                );
            }
        }
        // create order
        Order order = Order.fromCart(cart);
        // create payment
        Payment payment = Payment.forOrder(order);

        orderService.save(order);
        paymentService.save(payment);

        // create line item for stripe
        List<LineItemDto> lineItemDtoList = new ArrayList<>();
        order.getOrderItems().forEach(item -> {
            lineItemDtoList.add(
                    new LineItemDto(
                            item.getProductName(),
                            item.getUnitPrice(),
                            item.getQuantity()
                    )
            );
        });

        return new CheckoutInit(
                order.getOrderId(),
                payment.getPaymentId(),
                cart.getCartId(),
                order.getCurrency(),
                lineItemDtoList
        );
    }
}

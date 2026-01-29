package com.marwan.ecommerce.service.payment;
import com.marwan.ecommerce.exception.cart.CartEmptyException;
import com.marwan.ecommerce.exception.product.NotEnoughProductException;
import com.marwan.ecommerce.model.entity.Cart;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.model.entity.Payment;
import com.marwan.ecommerce.model.enums.OrderStatus;
import com.marwan.ecommerce.model.enums.PaymentProvider;
import com.marwan.ecommerce.repository.PaymentRepository;
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
@Transactional
public class CheckoutServiceTx
{
    private final CartService cartService;
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;


    public void finalizeCheckout(Payment payment, PaymentProvider paymentProvider, Cart cart,
            Order order, String sessionId)
    {
        payment.addPaymentProvider(paymentProvider);
        payment.setCheckoutSessionId(sessionId);
        cart.clear();
        order.setOrderStatus(OrderStatus.PAYMENT_PENDING);
        orderService.saveOrder(order);
        cartService.saveCart(cart);
        paymentRepository.save(payment);
    }

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

        orderService.saveOrder(order);
        paymentRepository.save(payment);

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
                order,
                payment,
                cart,
                lineItemDtoList
        );
    }
}

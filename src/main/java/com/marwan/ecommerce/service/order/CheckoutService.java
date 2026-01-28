package com.marwan.ecommerce.service.order;

import com.marwan.ecommerce.dto.order.CheckoutResponseDto;
import com.marwan.ecommerce.dto.order.CheckoutSessionDto;
import com.marwan.ecommerce.exception.cart.CartEmptyException;
import com.marwan.ecommerce.model.entity.Cart;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.model.entity.Payment;
import com.marwan.ecommerce.model.enums.OrderStatus;
import com.marwan.ecommerce.model.enums.PaymentProvider;
import com.marwan.ecommerce.repository.PaymentRepository;
import com.marwan.ecommerce.service.cart.CartService;
import com.marwan.ecommerce.service.order.command.CreateCheckoutSessionCommand;
import com.marwan.ecommerce.service.order.command.LineItemDto;
import com.marwan.ecommerce.service.payment.PaymentGateway;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService
{
    private final CartService cartService;
    private final OrderService orderService;
    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;


    public CheckoutResponseDto checkout(UUID userId)
    {
        var checkoutInit = initiateCheckout(userId);

        var createSessionCommand = new CreateCheckoutSessionCommand(
                checkoutInit.order().getOrderId(),
                checkoutInit.payment().getPaymentId(),
                checkoutInit.order().getCurrency().toString(),
                checkoutInit.lineItemDtoList()
        );
        CheckoutSessionDto session = paymentGateway.createCheckoutSession(createSessionCommand);
        finalizeCheckout(
                checkoutInit.payment(),
                session.paymentProvider(),
                checkoutInit.cart(),
                checkoutInit.order());

        return new CheckoutResponseDto(
                checkoutInit.order().getOrderId(),
                session.checkoutUrl()
        );
    }

    @Transactional
    public void finalizeCheckout(Payment payment, PaymentProvider paymentProvider, Cart cart,
            Order order)
    {
        payment.addPaymentProvider(paymentProvider);
        cart.clear();
        order.setOrderStatus(OrderStatus.PAYMENT_PENDING);
        orderService.saveOrder(order);
        cartService.saveCart(cart);
        paymentRepository.save(payment);
    }

    @Transactional
    public CheckoutInit initiateCheckout(UUID userId)
    {
        // returns order,payment,cart,and LineItemDtoList

        Cart cart = cartService.getCartWithUserId(userId);
        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }
        // create order
        Order order = Order.fromCart(cart);
        // create payment
        Payment payment = Payment.forOrder(order);

        orderService.saveOrder(order);
        paymentRepository.save(payment);

        // create checkout session from payment gateway
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
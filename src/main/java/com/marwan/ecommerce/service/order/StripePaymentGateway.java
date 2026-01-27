package com.marwan.ecommerce.service.order;

import com.marwan.ecommerce.dto.order.CheckoutSession;
import com.marwan.ecommerce.exception.order.PaymentException;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.model.entity.OrderItem;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripePaymentGateway implements PaymentGateway
{
    @Override
    public CheckoutSession createCheckoutSession(Order order)
    {

        try {

            // Create a checkout session
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://localhost:4242/success")
                    .setCancelUrl("https://localhost:4242/cancel")
                    .putMetadata("orderId", order.getOrderId().toString()); // put extra metadata

            order.getOrderItems().forEach(item -> {
                var lineItem = createLineItem(item);
                builder.addLineItem(lineItem);
            });
            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());
        } catch (StripeException e) {
            throw new PaymentException();
        }

    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item)
    {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(createPriceData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item)
    {

        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                .setProductData(createProductData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item)
    {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProductName())
                .build();
    }
}

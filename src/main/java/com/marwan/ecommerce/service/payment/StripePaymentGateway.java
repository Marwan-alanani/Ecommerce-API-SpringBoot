package com.marwan.ecommerce.service.payment;

import com.marwan.ecommerce.dto.order.CheckoutSessionDto;
import com.marwan.ecommerce.exception.payment.PaymentException;
import com.marwan.ecommerce.model.enums.PaymentProvider;
import com.marwan.ecommerce.service.order.command.CreateCheckoutSessionCommand;
import com.marwan.ecommerce.service.order.command.LineItemDto;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripePaymentGateway implements PaymentGateway
{
    @Override
    public CheckoutSessionDto createCheckoutSession(CreateCheckoutSessionCommand command)
    {

        try {

            // Create a checkout session
            var sessionCreateParams = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://localhost:4242/success")
                    .setCancelUrl("https://localhost:4242/cancel")
                    .setPaymentIntentData(
                            SessionCreateParams.PaymentIntentData.builder()
                                    .putMetadata("paymentId", command.paymentId().toString())
                                    .build()
                    );


            command.items().forEach(item -> {
                var lineItem = createLineItem(item, command.currency());
                sessionCreateParams.addLineItem(lineItem);
            });

            Session session = Session.create(sessionCreateParams.build());
            return new CheckoutSessionDto(
                    session.getUrl(),
                    PaymentProvider.STRIPE,
                    session.getId());
        } catch (StripeException e) {
            throw new PaymentException();
        }

    }

    private SessionCreateParams.LineItem createLineItem(LineItemDto item, String currency)
    {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(item.quantity())
                .setPriceData(createPriceData(item, currency))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(LineItemDto item,
            String currency)
    {

        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(currency.toLowerCase())
                .setUnitAmountDecimal(item.unitPrice().multiply(BigDecimal.valueOf(100)))
                .setProductData(createProductData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(LineItemDto item)
    {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.name())
                .build();
    }
}

package com.marwan.ecommerce.service.payment;

import com.marwan.ecommerce.dto.order.CheckoutResponseDto;
import com.marwan.ecommerce.dto.order.CheckoutSessionDto;
import com.marwan.ecommerce.service.order.command.CreateCheckoutSessionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService
{
    private final PaymentGateway paymentGateway;
    private final CheckoutServiceTx checkoutServiceTx;


    public CheckoutResponseDto checkout(UUID userId)
    {
        var checkoutInit = checkoutServiceTx.initiateCheckout(userId);

        var createSessionCommand = new CreateCheckoutSessionCommand(
                checkoutInit.payment().getPaymentId(),
                checkoutInit.order().getCurrency().toString(),
                checkoutInit.lineItemDtoList()
        );

        CheckoutSessionDto sessionDto = paymentGateway.createCheckoutSession(createSessionCommand);

        checkoutServiceTx.finalizeCheckout(
                checkoutInit.payment(),
                sessionDto.paymentProvider(),
                checkoutInit.cart(),
                checkoutInit.order(),
                sessionDto.sessionId());

        return new CheckoutResponseDto(
                checkoutInit.order().getOrderId(),
                sessionDto.checkoutUrl(),
                sessionDto.sessionId()
        );
    }
}
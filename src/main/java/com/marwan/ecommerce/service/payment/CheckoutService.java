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
                checkoutInit.paymentId(),
                checkoutInit.currency().toString(),
                checkoutInit.lineItemDtoList()
        );

        CheckoutSessionDto sessionDto = paymentGateway.createCheckoutSession(createSessionCommand);

        checkoutServiceTx.finalizeCheckout(
                checkoutInit.paymentId(),
                sessionDto.paymentProvider(),
                checkoutInit.cartId(),
                checkoutInit.orderId(),
                sessionDto.sessionId());

        return new CheckoutResponseDto(
                checkoutInit.orderId(),
                sessionDto.checkoutUrl(),
                sessionDto.sessionId()
        );
    }
}
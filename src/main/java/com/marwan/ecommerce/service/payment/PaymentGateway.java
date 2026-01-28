package com.marwan.ecommerce.service.payment;

import com.marwan.ecommerce.dto.order.CheckoutSessionDto;
import com.marwan.ecommerce.service.order.command.CreateCheckoutSessionCommand;

public interface PaymentGateway
{
    CheckoutSessionDto createCheckoutSession(CreateCheckoutSessionCommand command);

}

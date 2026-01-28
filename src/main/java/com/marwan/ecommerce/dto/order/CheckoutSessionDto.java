package com.marwan.ecommerce.dto.order;

import com.marwan.ecommerce.model.enums.PaymentProvider;

public record CheckoutSessionDto(
        String checkoutUrl,
        PaymentProvider paymentProvider
)
{
}

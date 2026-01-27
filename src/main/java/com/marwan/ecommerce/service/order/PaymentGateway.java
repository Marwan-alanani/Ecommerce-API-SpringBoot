package com.marwan.ecommerce.service.order;

import com.marwan.ecommerce.dto.order.CheckoutSession;
import com.marwan.ecommerce.model.entity.Order;

public interface PaymentGateway
{
    CheckoutSession createCheckoutSession(Order order);

}

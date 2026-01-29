package com.marwan.ecommerce.service.payment;

import com.marwan.ecommerce.model.enums.Currency;
import com.marwan.ecommerce.service.order.command.LineItemDto;

import java.util.List;
import java.util.UUID;

public record CheckoutInit(
        UUID orderId,
        UUID paymentId,
        UUID cartId,
        Currency currency,
        List<LineItemDto> lineItemDtoList
)
{

}

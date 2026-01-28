package com.marwan.ecommerce.service.order;

import com.marwan.ecommerce.model.entity.Cart;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.model.entity.Payment;
import com.marwan.ecommerce.service.order.command.LineItemDto;

import java.util.List;

public record CheckoutInit(
        Order order,
        Payment payment,
        Cart cart,
        List<LineItemDto> lineItemDtoList
)
{

}

package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.dto.order.OrderItemDto;
import com.marwan.ecommerce.dto.order.OrderDto;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.model.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper
{
    OrderDto orderEntityToOrderDto(Order order);

    OrderItemDto orderItemEntityToOrderItemDto(OrderItem orderItem);

}

package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.controller.order.OrderItemDto;
import com.marwan.ecommerce.dto.order.OrderDto;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper
{
    OrderDto orderEntityToOrderDto(Order order);

    OrderItemDto orderItemEntityToOrderItemDto(OrderItem orderItem);

}

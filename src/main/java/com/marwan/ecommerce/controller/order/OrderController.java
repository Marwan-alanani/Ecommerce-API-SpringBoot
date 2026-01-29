package com.marwan.ecommerce.controller.order;

import com.marwan.ecommerce.dto.order.OrderDto;
import com.marwan.ecommerce.mapper.OrderMapper;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController
{
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<?> findAll()
    {
        List<Order> orders = orderService.getAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        orders.forEach(order -> {
            orderDtos.add(orderMapper.orderEntityToOrderDto(order));
        });
        return ResponseEntity.ok(orderDtos);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable UUID orderId)
    {
        Order order = orderService.getOrderWithOrderItems(orderId);
        OrderDto orderDto = orderMapper.orderEntityToOrderDto(order);
        return ResponseEntity.ok(orderDto);
    }

}

package com.marwan.ecommerce.controller.order;

import com.marwan.ecommerce.controller.common.converter.BaseController;
import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.dto.order.OrderDto;
import com.marwan.ecommerce.dto.order.OrderPagingOptions;
import com.marwan.ecommerce.mapper.OrderMapper;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.security.CustomUserDetails;
import com.marwan.ecommerce.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController extends BaseController
{
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<PageDto<OrderDto>> findAll(
            @Valid OrderPagingOptions pagingOptions,
            @AuthenticationPrincipal CustomUserDetails userDetails
    )
    {
        Page<Order> orderPage = orderService.getAll(pagingOptions, userDetails);
        List<OrderDto> orderDtos = orderPage.stream()
                .map(orderMapper::orderEntityToOrderDto)
                .toList();
        return ResponseEntity.ok(toPageDto(orderPage, orderDtos));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Order order = orderService.getOrderWithOrderItems(orderId, userDetails);
        OrderDto orderDto = orderMapper.orderEntityToOrderDto(order);
        return ResponseEntity.ok(orderDto);
    }

}

package com.marwan.ecommerce.controller.order;

import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.dto.order.OrderDto;
import com.marwan.ecommerce.mapper.OrderMapper;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.model.enums.UserRole;
import com.marwan.ecommerce.security.CustomUserDetails;
import com.marwan.ecommerce.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.marwan.ecommerce.controller.common.BaseController.toPageDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController
{
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageDto<OrderDto>> getAll(
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable
    )
    {
        Page<Order> orderPage = orderService.getAll(pageable);
        List<OrderDto> orderDtos = orderPage.stream()
                .map(orderMapper::orderEntityToOrderDto)
                .toList();
        return ResponseEntity.ok(toPageDto(orderPage, orderDtos));
    }

    @GetMapping("/me")
    public ResponseEntity<PageDto<OrderDto>> getUserOrders(
            @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Page<Order> orderPage = orderService.getUserOrders(pageable, userDetails.getUserId());
        List<OrderDto> orderDtos = orderPage.stream()
                .map(orderMapper::orderEntityToOrderDto)
                .toList();
        return ResponseEntity.ok(toPageDto(orderPage, orderDtos));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Order order = orderService.getOrderWithOrderItems(
                orderId,
                userDetails.getUserId(),
                userDetails.getRole() == UserRole.ADMIN
        );
        OrderDto orderDto = orderMapper.orderEntityToOrderDto(order);
        return ResponseEntity.ok(orderDto);
    }

}

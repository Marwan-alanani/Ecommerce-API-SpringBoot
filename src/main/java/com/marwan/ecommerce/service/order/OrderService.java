package com.marwan.ecommerce.service.order;

import com.marwan.ecommerce.dto.order.OrderPagingOptions;
import com.marwan.ecommerce.exception.order.OrderNotFoundException;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.repository.OrderRepository;
import com.marwan.ecommerce.service.common.BaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService extends BaseService
{
    private final OrderRepository orderRepository;

    public Page<Order> getAll(OrderPagingOptions pagingOptions)
    {
        var pageable =  constructPageable(pagingOptions);
        return orderRepository.findAllWithOrderItems(pageable);
    }

    public Order getOrderWithOrderItems(UUID orderId)
    {
        return orderRepository.findByOrderIdWithOrderItems(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId));
    }

    public Order getOrder(UUID orderId)
    {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId));
    }

    @Transactional
    public void save(Order order)
    {
        orderRepository.save(order);
    }
}

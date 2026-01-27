package com.marwan.ecommerce.service.order;

import com.marwan.ecommerce.exception.order.OrderNotFoundException;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;

    public List<Order> getAll()
    {
        return orderRepository.findAllWithOrderItems();
    }

    public Order getOrderById(UUID orderId)
    {
        return orderRepository.findByOrderIdWithOrderItems(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId));
    }
}

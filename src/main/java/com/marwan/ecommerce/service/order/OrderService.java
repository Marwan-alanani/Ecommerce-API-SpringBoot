package com.marwan.ecommerce.service.order;

import com.marwan.ecommerce.exception.order.OrderNotFoundException;
import com.marwan.ecommerce.model.entity.Order;
import com.marwan.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;


    public Page<Order> getUserOrders(Pageable pageable, UUID userId)
    {
        return orderRepository.findAllByUserIdWithOrderItems(pageable, userId);
    }

    public Page<Order> getAll(Pageable  pageable)
    {
        return orderRepository.findAllWithOrderItems(pageable);
    }


    public Order getOrderWithOrderItems(UUID orderId, UUID userId, boolean isAdmin)
    {
        if (isAdmin) {
            return orderRepository.findByOrderIdWithOrderItems(orderId).orElseThrow(
                    () -> new OrderNotFoundException(orderId));
        }

        return orderRepository.findByUserIdAndOrderIdWithOrderItems(
                        userId,
                        orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public Order getOrder(UUID orderId , boolean isAdmin , UUID userId)
    {

        if (isAdmin) {
            return orderRepository
                    .findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException(orderId));
        }
        return orderRepository
                .findByUserIdAndOrderId(userId, orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Transactional
    public void save(Order order)
    {
        orderRepository.save(order);
    }
}

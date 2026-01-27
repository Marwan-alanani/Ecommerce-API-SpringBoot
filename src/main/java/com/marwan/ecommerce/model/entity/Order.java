package com.marwan.ecommerce.model.entity;

import com.marwan.ecommerce.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Order
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID orderId;

    private UUID userId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    @Setter(AccessLevel.PRIVATE)
    private List<OrderItem> orderItems;

    @Setter(AccessLevel.PRIVATE)
    private BigDecimal totalPrice;

    private Instant createdDateTime;

    // order status
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public List<OrderItem> getOrderItems()
    {
        return new ArrayList<>(orderItems);
    }

    public void addOrderItem(OrderItem orderItem)
    {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order fromCart(Cart cart)
    {

        // create empty order
        Order order = new Order(
                UUID.randomUUID(),
                cart.getUserId(),
                new ArrayList<>(),
                BigDecimal.ZERO,
                Instant.now(),
                OrderStatus.CREATED
        );

        // populate order items and compute total price
        cart.getCartItems().forEach(item -> {
            order.addOrderItem(OrderItem.fromCartItem(item));
        });
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(OrderItem orderItem : order.getOrderItems()) {
            totalPrice = totalPrice.add(orderItem.getTotalPrice());
        }
        order.setTotalPrice(totalPrice);
        return order;
    }
}

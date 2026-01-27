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
    @Setter(AccessLevel.NONE)
    private List<OrderItem> orderItems;

    private BigDecimal totalPrice;

    private Instant createdDateTime;

    // order status
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public List<OrderItem> getOrderItems()
    {
        return new ArrayList<>(orderItems);
    }

    public static Order fromCart(Cart cart)
    {
        List<OrderItem> orderItems = new ArrayList<>();
        cart.getCartItems().forEach(item -> {
            orderItems.add(OrderItem.fromCartItem(item));
        });
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems)
            totalPrice = totalPrice.add(orderItem.getTotalPrice());


        return new Order(
                UUID.randomUUID(),
                cart.getUserId(),
                orderItems,
                totalPrice,
                Instant.now(),
                OrderStatus.CREATED
        );
    }
}

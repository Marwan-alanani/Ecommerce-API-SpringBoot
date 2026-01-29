package com.marwan.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "order_items")
public final class OrderItem
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID orderItemId;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String productPictureUrl;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public BigDecimal getTotalPrice()
    {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public static OrderItem fromCartItem(
            CartItem cartItem
    )
    {
        return new OrderItem(
                UUID.randomUUID(),
                cartItem.getProduct().getProductId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPictureUrl(),
                cartItem.getProduct().getSellingPrice(),
                cartItem.getQuantity(),
                null
        );
    }
}

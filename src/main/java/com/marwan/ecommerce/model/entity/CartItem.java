package com.marwan.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public final class CartItem
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID cartItemId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @CreationTimestamp
    private Instant createdDateTime;

    @UpdateTimestamp
    private Instant updatedDateTime;

    public BigDecimal getTotalPrice()
    {
        return product.getSellingPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public static CartItem fromProduct(Product product)
    {
        Instant now = Instant.now();

        return new CartItem(
                UUID.randomUUID(),
                product,
                1,
                null,
                now,
                now);

    }

    public static CartItem fromProductWithQuantity(Product product, int quantity)
    {
        Instant now = Instant.now();
        return new CartItem(
                UUID.randomUUID(),
                product,
                quantity,
                null,
                now,
                now);
    }


}

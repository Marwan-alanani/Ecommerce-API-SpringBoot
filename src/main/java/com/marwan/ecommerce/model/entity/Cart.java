package com.marwan.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "carts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public final class Cart
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID cartId;
    @Column(nullable = false)
    private UUID userId;

    @CreationTimestamp
    private Instant createdDateTime;

    @UpdateTimestamp
    private Instant updatedDateTime;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "cart",
            orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<CartItem> cartItems;


    public boolean isEmpty()
    {
        return cartItems.isEmpty();
    }

    public BigDecimal getTotalCost()
    {
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CartItem> getCartItems()
    {
        // return a copy
        return new ArrayList<>(cartItems);
    }

    public void addCartItem(CartItem cartItem)
    {
        cartItem.setCart(this);
        cartItems.add(cartItem);
    }

    public CartItem getCartItemByProductId(UUID productId)
    {
        return cartItems.stream().filter(i -> i.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public void remove(UUID productId)
    {

        CartItem item = cartItems.stream()
                .filter(b -> b.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item != null) {
            item.setCart(null);
            cartItems.remove(item);
        }

    }

    public void clear()
    {
        cartItems.clear();
    }

    public static Cart create(UUID userId)
    {
        Instant now = Instant.now();
        return new Cart(
                UUID.randomUUID(),
                userId,
                now,
                now,
                new ArrayList<>()
        );
    }
}

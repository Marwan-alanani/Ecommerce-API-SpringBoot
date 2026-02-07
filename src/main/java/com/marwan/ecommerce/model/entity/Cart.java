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

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdDateTime;

    @UpdateTimestamp
    private Instant updatedDateTime;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cart",
            orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<CartItem> cartItems = new ArrayList<>();


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
        CartItem existingItem = getCartItemByProductId(cartItem.getProduct().getProductId());
        if (existingItem == null) {
            cartItem.setCart(this);
            cartItems.add(cartItem);
        } else {
            existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
        }
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
        return new Cart(
                UUID.randomUUID(),
                userId,
                null,
                null,
                new ArrayList<>()
        );
    }
}

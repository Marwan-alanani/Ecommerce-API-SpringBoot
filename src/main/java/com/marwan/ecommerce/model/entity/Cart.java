package com.marwan.ecommerce.model.entity;

import com.marwan.ecommerce.exception.product.ProductIdNotFoundException;
import com.marwan.ecommerce.model.enums.BasketStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime updatedDateTime;


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
        LocalDateTime now = LocalDateTime.now();
        return new Cart(
                UUID.randomUUID(),
                userId,
                now,
                now,
                new ArrayList<>()
        );
    }

}

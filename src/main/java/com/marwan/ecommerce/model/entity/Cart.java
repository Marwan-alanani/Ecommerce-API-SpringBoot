package com.marwan.ecommerce.model.entity;

import com.marwan.ecommerce.exception.product.ProductIdNotFoundException;
import com.marwan.ecommerce.model.enums.BasketStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "carts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Cart
{
    @Id
    private UUID basketId;
    @Column(nullable = false)

    private UUID userId;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime updatedDateTime;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "cart", orphanRemoval = true)
    private List<CartItem> cartItems;

    public boolean isEmpty()
    {
        return cartItems.isEmpty();
    }

    public List<CartItem> getCartItems()
    {
        // return a copy
        return new ArrayList<>(cartItems);
    }

    public void addOrMergeBasketItem(CartItem cartItem)
    {
        Optional<CartItem> optionalBasketItem = cartItems.stream()
                .filter(b -> b.getProduct().getProductId().equals(
                        cartItem.getProduct().getProductId()))
                .findFirst();
        if (optionalBasketItem.isPresent()) {
            CartItem item = optionalBasketItem.get();
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
        } else {
            cartItem.setCart(this);
            cartItems.add(cartItem);
        }

    }

    public void remove(UUID productId)
            throws ProductIdNotFoundException
    {

        CartItem item = cartItems.stream()
                .filter(b -> b.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductIdNotFoundException(productId));

        item.setCart(null);
        cartItems.remove(item);
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

package com.marwan.ecommerce.model.entity;

import com.marwan.ecommerce.model.enums.BasketStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

@Entity
@Table(name = "baskets")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Basket
{
    @Id
    private UUID basketId;
    @Column(nullable = false)

    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BasketStatus status;

    @CreatedDate
    private Date createdDateTime;

    @LastModifiedDate
    private Date updatedDateTime;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "basket", orphanRemoval = true)
    private List<BasketItem> basketItems;


    public List<BasketItem> getBasketItems()
    {
        // return a copy
        return new ArrayList<>(basketItems);
    }

    public void addOrMergeBasketItem(BasketItem basketItem)
            throws Exception
    {
        if (basketItem == null)
            throw new Exception();


        Optional<BasketItem> optionalBasketItem = basketItems.stream()
                .filter(b -> b.getProductId().equals(basketItem.getProductId()))
                .findFirst();
        if (optionalBasketItem.isPresent()) {
            BasketItem item = optionalBasketItem.get();
            item.setQuantity(item.getQuantity() + basketItem.getQuantity());
        } else {
            basketItem.setBasket(this);
            basketItems.add(basketItem);
        }


    }

    public void remove(UUID basketItemId)
            throws Exception
    {
        if (basketItemId == null)
            throw new Exception();
        BasketItem item = basketItems.stream()
                .filter(b -> b.getBasketItemId().equals(basketItemId))
                .findFirst()
                .orElseThrow(() -> new Exception());

        item.setBasket(null);
        basketItems.remove(item);
    }

    public static Basket create(UUID userId)
    {
        Date now = new Date();
        return new Basket(
                UUID.randomUUID(),
                userId,
                BasketStatus.ACTIVE,
                now,
                now,
                new ArrayList<>()
        );
    }

}

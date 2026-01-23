package com.marwan.ecommerce.model.entity;

import com.marwan.ecommerce.model.enums.BasketStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private BasketStatus status;
    private Date createdDateTime;
    private Date updatedDateTime;
    private List<BasketItem> basketItems;


    public List<BasketItem> getBasketItems()
    {
        // return a copy
        return new ArrayList<>(basketItems);
    }

    public void addBasketItem(BasketItem basketItem)
    {
        basketItems.add(basketItem);
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

package com.marwan.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "basket_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class BasketItem
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID basketItemId;

    @Column(nullable = false)
    private UUID productId;
    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @CreatedDate
    private Date createdDateTime;

    @LastModifiedDate
    private Date updatedDateTime;

    public static BasketItem create(UUID productId, int quantity)
    {
        Date now = new Date();
        return new BasketItem(
                UUID.randomUUID(),
                productId,
                quantity,
                null,
                now,
                now
        );
    }


}

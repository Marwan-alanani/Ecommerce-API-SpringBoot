package com.marwan.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "purchase")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Purchase
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID purchaseId;
    @Column(nullable = false)
    private UUID productId;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private UUID supplierId;
    @Column(nullable = false)

    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDateTime;

    public static Purchase create(
            UUID productId,
            double price,
            int quantity,
            UUID supplierId
    )
    {
        return new Purchase(
                UUID.randomUUID(),
                productId,
                price,
                quantity,
                supplierId,
                LocalDateTime.now()
        );

    }

}

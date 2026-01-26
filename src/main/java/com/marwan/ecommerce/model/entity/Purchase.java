package com.marwan.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private UUID supplierId;
    @Column(nullable = false)

    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDateTime;

    public static Purchase create(
            UUID productId,
            double unitPrice,
            int quantity,
            UUID supplierId
    )
    {
        return new Purchase(
                UUID.randomUUID(),
                productId,
                BigDecimal.valueOf(unitPrice),
                quantity,
                supplierId,
                LocalDateTime.now()
        );

    }

}

package com.marwan.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
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
    @CreationTimestamp
    private Instant createdDateTime;

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
                null
        );

    }

    public BigDecimal getTotalCost()
    {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

}

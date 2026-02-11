package com.marwan.ecommerce.model.entity;

import com.marwan.ecommerce.service.purchase.event.purchaseCreated.PurchaseCreatedEvent;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "purchase")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Purchase extends AbstractAggregateRoot<Purchase>
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

    @CreationTimestamp
    private Instant createdDateTime;

    public static Purchase create(
            UUID productId,
            BigDecimal unitPrice,
            int quantity,
            UUID supplierId
    )
    {
        Purchase purchase = new Purchase(
                UUID.randomUUID(),
                productId,
                unitPrice,
                quantity,
                supplierId,
                null
        );
        purchase.registerEvent(new PurchaseCreatedEvent(productId, unitPrice, quantity));
        return purchase;
    }

    public BigDecimal getTotalCost()
    {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

}

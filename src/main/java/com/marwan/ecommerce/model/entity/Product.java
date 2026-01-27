package com.marwan.ecommerce.model.entity;

import com.marwan.ecommerce.exception.product.NotEnoughProductException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Product
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID productId;
    @Column(nullable = false)
    private String name;

    // description is nullable
    private String description;

    @Column(nullable = false, name = "price")
    private BigDecimal sellingPrice;

    @Column(nullable = false)
    private String pictureUrl;

    @Column(nullable = false)
    private int balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private Instant createdDateTime;
    @Column(nullable = false)
    @UpdateTimestamp
    private Instant updatedDateTime;

    @Column(nullable = false)
    private boolean isEnabled;

    @Column(nullable = false)
    private BigDecimal totalPurchasePrice;
    @Column(nullable = false)
    private long totalPurchaseQuantity;

    public void decreaseBalance(int quantity)
    {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero");

        if (balance < quantity)
            throw new NotEnoughProductException(name, balance, quantity);

        balance -= quantity;
    }

    public void increaseBalance(int quantity)
    {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero");

        balance += quantity;
    }

    public static Product create(
            String name,
            String description,
            double price,
            String pictureUrl,
            Category category)
    {
        Instant now = Instant.now();
        return new Product(
                UUID.randomUUID(),
                name,
                description,
                BigDecimal.valueOf(price),
                pictureUrl,
                0,
                category,
                now,
                now,
                true,
                BigDecimal.ZERO,
                0
        );
    }


}

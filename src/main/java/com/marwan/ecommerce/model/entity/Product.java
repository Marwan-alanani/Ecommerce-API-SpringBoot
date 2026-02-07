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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Product
{
    @Id
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdDateTime;
    @Column(nullable = false)
    @UpdateTimestamp
    private Instant updatedDateTime;

    @Column(nullable = false)
    private boolean isEnabled;

    @Column(nullable = false)
    @Setter
    private BigDecimal totalPurchasePrice;

    @Column(nullable = false)
    @Setter
    private long totalPurchaseQuantity;

    @Version
    @Column(nullable = false)
    private long version;

    public void setName(String name)
    {
        if (name == null)
            return;

        if (name.isBlank())
            throw new IllegalArgumentException("Product name cannot be blank");
        this.name = name;
    }

    public void setDescription(String description)
    {
        if (description == null)
            return;
        this.description = description;
    }

    public void setSellingPrice(BigDecimal price)
    {
        if (price == null)
            return;
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.sellingPrice = price;
    }

    public void setPictureUrl(String pictureUrl)
    {
        if (pictureUrl == null)
            return;
        this.pictureUrl = pictureUrl;
    }

    public void setCategory(Category category)
    {
        if (category == null)
            return;
        this.category = category;
    }

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
            BigDecimal price,
            String pictureUrl,
            Category category)
    {
        return new Product(
                UUID.randomUUID(),
                name,
                description,
                price,
                pictureUrl,
                0,
                category,
                null,
                null,
                true,
                BigDecimal.ZERO,
                0,
                0L
        );
    }

    public void deactivate()
    {
        isEnabled = false;
    }


}

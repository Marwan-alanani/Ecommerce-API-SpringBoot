package com.marwan.ecommerce.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
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

    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String pictureUrl;

    @Column(nullable = false)
    private int balance;

    @Column(nullable = false)
    private UUID categoryId;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Date createdDateTime;
    @Column(nullable = false)
    private Date updatedDateTime;

    @Column(nullable = false)
    private double maxPurchasePrice;
    private boolean isEnabled;

    public static Product create(
            String name,
            String description,
            double price,
            String pictureUrl,
            UUID categoryId)
    {
        Date currentDate = new Date();
        return new Product(
                UUID.randomUUID(),
                name,
                description,
                price,
                pictureUrl,
                0,
                categoryId,
                currentDate,
                currentDate,
                0,
                true
        );
    }


}

package com.marwan.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "basket_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public final class CartItem
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID cartItemId;


    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private Cart cart;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime updatedDateTime;

    public BigDecimal getTotalPrice()
    {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public static CartItem fromProduct(Product product)
    {
        LocalDateTime createdDateTime = LocalDateTime.now();

        return new CartItem(
                UUID.randomUUID(),
                product,
                1,
                null,
                createdDateTime,
                createdDateTime);

    }

    public static CartItem fromProductWithQuantity(Product product, int quantity)
    {
        LocalDateTime createdDateTime = LocalDateTime.now();
        return new CartItem(
                UUID.randomUUID(),
                product,
                quantity,
                null,
                createdDateTime,
                createdDateTime);
    }


}

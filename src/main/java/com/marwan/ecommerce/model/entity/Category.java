package com.marwan.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "category")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Category
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID categoryId;
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDateTime;

    @Column(nullable = false)
    private LocalDateTime updatedDateTime;
    private boolean isEnabled;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private List<Product> products;

    public static Category create(String name)
    {
        LocalDateTime now = LocalDateTime.now();
        return new Category(
                UUID.randomUUID(),
                name,
                now,
                now,
                true,
                null);
    }
}

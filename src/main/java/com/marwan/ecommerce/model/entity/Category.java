package com.marwan.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
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

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private Instant createdDateTime;

    @Column(nullable = false)
    @UpdateTimestamp
    private Instant updatedDateTime;
    private boolean isEnabled;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY,
            mappedBy = "category")
    @Setter(AccessLevel.NONE)
    private List<Product> products;

    public static Category create(String name)
    {
        Instant now = Instant.now();
        return new Category(
                UUID.randomUUID(),
                name,
                now,
                now,
                true,
                null);
    }
}

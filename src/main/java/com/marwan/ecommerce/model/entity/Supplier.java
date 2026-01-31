package com.marwan.ecommerce.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "supplier")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Supplier
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID supplierId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private Instant createdDateTime;

    @Column(nullable = false)
    @UpdateTimestamp
    private Instant updatedDateTime;
    private boolean isEnabled;

    public static Supplier create(String name, String email)
    {
        return new Supplier(
                UUID.randomUUID(),
                name,
                email,
                null,
                null,
                true
        );
    }

}

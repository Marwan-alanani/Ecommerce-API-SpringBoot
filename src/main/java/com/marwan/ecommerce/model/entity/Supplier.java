package com.marwan.ecommerce.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "supplier")
@Data
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
    private Date createdDateTime;

    @Column(nullable = false)
    private Date updatedDateTime;
    private boolean isEnabled;

    public static Supplier create(String name, String email)
    {
        Date currentDate = new Date();
        return new Supplier(
                UUID.randomUUID(),
                name,
                email,
                currentDate,
                currentDate,
                true
        );
    }

}

package com.marwan.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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
    private Date createdDateTime;

    @Column(nullable = false)
    private Date updatedDateTime;
    private boolean isEnabled;

    //    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //    @Setter(AccessLevel.NONE)
    //    private List<Product> products;

    public static Category create(String name)
    {
        Date currentDate = new Date();
        return new Category(
                UUID.randomUUID(),
                name,
                currentDate,
                currentDate,
                true);
    }
}

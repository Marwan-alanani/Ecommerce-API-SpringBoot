package com.marwan.ecommerce.model.category.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "category")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category
{
    @Id
    @Setter(AccessLevel.NONE)
    private UUID categoryId;
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Date createdDateTime;

    @Column(nullable = false)
    private Date updatedDateTime;

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
                currentDate);
    }

    //    public void addProduct(Product product)
    //    {
    //        this.products.add(product);
    //    }
}

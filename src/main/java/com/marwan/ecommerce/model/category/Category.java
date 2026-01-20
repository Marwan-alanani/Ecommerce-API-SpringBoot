package com.marwan.ecommerce.model.category;

import jakarta.persistence.*;
import lombok.*;
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
    private UUID id;
    @Column(unique = true, nullable = false)
    private String name;

    //    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //    @Setter(AccessLevel.NONE)
    //    private List<Product> products;

    public static Category create(String name)
    {
        return new Category(UUID.randomUUID(), name);
    }

//    public void addProduct(Product product)
//    {
//        this.products.add(product);
//    }
}

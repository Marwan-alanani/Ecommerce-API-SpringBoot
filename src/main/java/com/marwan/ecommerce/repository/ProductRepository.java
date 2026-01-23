package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>
{
    Optional<Product> findByProductIdAndIsEnabledTrue(UUID productId);

    List<Product> findByCategoryId(UUID categoryId);

    boolean existsByProductIdAndIsEnabledTrue(UUID productId);

    List<Product> findByCategoryIdAndIsEnabledTrue(UUID categoryId);

    List<Product> findAllByIsEnabledTrue();

    int countByCategoryId(UUID categoryId);

    int countByCategoryIdAndIsEnabledTrue(UUID categoryId);
}

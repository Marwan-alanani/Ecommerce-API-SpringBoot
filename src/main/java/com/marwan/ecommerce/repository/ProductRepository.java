package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>
{
    Optional<Product> findByProductIdAndIsEnabled(UUID productId, boolean isEnabled);

    @EntityGraph(attributePaths = "category")
    Optional<Product> findByProductIdAndIsEnabledWithCategory(UUID productId, boolean isEnabled);

    List<Product> findByCategoryId(UUID categoryId);

    boolean existsByProductIdAndIsEnabled(UUID productId, boolean isEnabled);

    List<Product> findByCategoryIdAndIsEnabled(UUID categoryId, boolean isEnabled);

    List<Product> findAllByIsEnabled(boolean isEnabled);

    int countByCategoryIdAndIsEnabled(UUID categoryId, boolean isEnabled);
}

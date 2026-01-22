package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID>
{
    List<Purchase> findByProductId(UUID productId);

    List<Purchase> findBySupplierId(UUID supplierId);

    List<Purchase> findBySupplierIdAndProductId(UUID supplierId, UUID productId);
}

package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID>
{
    @Override
    Page<Purchase> findAll(Pageable pageable);

    Page<Purchase> findByProductId(Pageable pageable, UUID productId);

    Page<Purchase> findBySupplierId(Pageable pageable, UUID supplierId);

    Page<Purchase> findBySupplierIdAndProductId(Pageable pageable, UUID supplierId, UUID productId);
}

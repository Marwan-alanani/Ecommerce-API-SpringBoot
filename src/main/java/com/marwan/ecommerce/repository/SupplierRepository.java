package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Supplier;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID>
{
    @Override
    @NullMarked
    Page<Supplier> findAll(Pageable pageable);

    Page<Supplier> findAllByIsEnabled(Pageable pageable, boolean isEnabled);

    Optional<Supplier> findBySupplierId(UUID id);

    boolean existsByName(String name);


    boolean existsByEmail(String email);


    int countByName(String name);


    int countByEmail(String email);

    boolean existsBySupplierIdAndIsEnabled(UUID supplierId, boolean isEnabled);

}

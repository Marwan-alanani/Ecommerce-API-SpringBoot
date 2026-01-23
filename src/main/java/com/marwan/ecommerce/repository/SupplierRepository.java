package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID>
{
    Optional<Supplier> findBySupplierIdAndIsEnabledTrue(UUID id);

    Optional<Supplier> findByName(String name);

    Optional<Supplier> findByNameAndIsEnabledTrue(String name);

    Optional<Supplier> findByEmail(String name);

    Optional<Supplier> findByEmailAndIsEnabledTrue(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIsEnabledTrue(String name);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIsEnabledTrue(String email);

    int countByName(String name);

    int countByNameAndIsEnabledTrue(String name);

    int countByEmail(String email);

    int countByEmailAndIsEnabledTrue(String name);

    boolean existsBySupplierIdAndIsEnabledTrue(UUID id);
}

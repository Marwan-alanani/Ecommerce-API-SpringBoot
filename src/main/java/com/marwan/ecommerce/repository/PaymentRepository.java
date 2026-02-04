package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Payment;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID>
{
    @EntityGraph(attributePaths = "order")
    Page<Payment> findAllByOrder_UserId(Pageable pageable, UUID userId);

    @Override
    @EntityGraph(attributePaths = "order")
    @NullMarked
    Page<Payment> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "order")
    Optional<Payment> findByPaymentIdAndOrder_UserId(UUID paymentId, UUID userId);

    @EntityGraph(attributePaths = "order")
    Optional<Payment> findByPaymentId(UUID paymentId);
}

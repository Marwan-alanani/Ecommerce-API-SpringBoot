package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID>
{
    @EntityGraph(attributePaths = "order")
    Page<Payment> findAllByUserId(Pageable pageable, UUID userId);

    @Override
    @EntityGraph(attributePaths = "order")
    Page<Payment> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "order")
    Optional<Payment> findByPaymentIdAndUserId(UUID paymentId, UUID userId);

    @EntityGraph(attributePaths = "order")
    Optional<Payment> findByPaymentId(UUID paymentId);
}

package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID>
{
}

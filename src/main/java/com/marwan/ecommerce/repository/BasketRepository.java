package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BasketRepository extends JpaRepository<Cart, UUID>
{
    Optional<Cart> findBasketByUserId(UUID userId);

}

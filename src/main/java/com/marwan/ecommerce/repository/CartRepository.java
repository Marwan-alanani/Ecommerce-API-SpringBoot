package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID>
{
    Optional<Cart> findByUserId(UUID userId);

    @EntityGraph(attributePaths = "cartItems.product")
    @Query("SELECT c FROM Cart c WHERE c.cartId = :cartId")
    Optional<Cart> getCartWithItems(@Param("cartId") UUID cartId);



    @EntityGraph(attributePaths = "cartItems.product")
    @Query("SELECT c FROM Cart c WHERE c.userId = :userId")
    Optional<Cart> getCartWithItemsByUserId(@Param("userId") UUID userid);

}

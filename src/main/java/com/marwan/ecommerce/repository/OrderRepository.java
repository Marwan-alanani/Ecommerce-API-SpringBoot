package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Order;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>
{
    @Override
    @NullMarked
    Page<Order> findAll(Pageable pageable);

    Optional<Order> findByUserIdAndOrderId(UUID userId, UUID orderId);

    @EntityGraph(attributePaths = "orderItems")
    @Query("SELECT o FROM Order o")
    Page<Order> findAllWithOrderItems(Pageable pageable);


    @EntityGraph(attributePaths = "orderItems")
    @Query("SELECT o FROM Order o WHERE o.orderId=:orderId")
    Optional<Order> findByOrderIdWithOrderItems(@Param("orderId") UUID orderId);


    @EntityGraph(attributePaths = "orderItems")
    @Query("SELECT o FROM Order o WHERE o.userId=:userId AND o.orderId=:orderId")
    Optional<Order> findByUserIdAndOrderIdWithOrderItems(
            @Param("userId") UUID userId,
            @Param("orderId") UUID orderId
    );


    @EntityGraph(attributePaths = "orderItems")
    @Query("SELECT o FROM Order o WHERE o.userId=:userId")
    Page<Order> findAllByUserIdWithOrderItems(Pageable pageable, @Param("userId") UUID userId);
}

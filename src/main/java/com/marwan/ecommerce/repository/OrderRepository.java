package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>
{
    @EntityGraph(attributePaths = "orderItems")
    @Query("SELECT o FROM Order o")
    List<Order> findAllWithOrderItems();


    @EntityGraph(attributePaths = "orderItems")
    @Query("SELECT o FROM Order o WHERE o.orderId=:orderId")
    Optional<Order> findByOrderIdWithOrderItems(@Param("orderId") UUID orderId);

    @EntityGraph(attributePaths = "orderItems")
    @Query("SELECT o FROM Order o WHERE o.userId=:userId")
    List<Order> findByUserIdWithOrderItems(@Param("userId") UUID orderId);
}

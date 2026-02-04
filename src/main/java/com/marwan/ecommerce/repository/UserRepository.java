package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.User;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>
{
    Optional<User> findByEmail(String email);

    boolean existsByUserIdAndIsEnabled(UUID userId, boolean isEnabled);

    @Override
    @NullMarked
    Page<User> findAll(Pageable pageable);
}

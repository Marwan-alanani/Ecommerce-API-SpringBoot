package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID>
{
    @Override
    Page<Category> findAll(Pageable pageable);

    Optional<Category> findByName(String name);

    Optional<Category> findByCategoryIdAndIsEnabled(UUID categoryId, boolean isEnabled);


    Optional<Category> findByNameAndIsEnabled(String name, boolean isEnabled);

    int countByName(String name);

    Page<Category> findAllByIsEnabled(Pageable pageable, boolean isEnabled);

}

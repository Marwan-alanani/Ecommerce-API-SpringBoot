package com.marwan.ecommerce.repository;

import com.marwan.ecommerce.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID>
{
    Optional<Category> findByName(String name);

    Optional<Category> findByCategoryIdAndIsEnabled(UUID categoryId, boolean isEnabled);


    Optional<Category> findByNameAndIsEnabled(String name, boolean isEnabled);

    int countByName(String name);

    List<Category> findAllByIsEnabled(boolean isEnabled);

}

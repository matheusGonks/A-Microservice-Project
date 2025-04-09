package com.shop_style.catalog_service.repositories;

import com.shop_style.catalog_service.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = {"childrenCategories"})
    List<Category> findByParentIsNull();

    @EntityGraph(attributePaths = {"childrenCategories"})
    Optional<Category> findWithChildrenById(Long id);
}

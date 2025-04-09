package com.shop_style.catalog_service.repositories;

import com.shop_style.catalog_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

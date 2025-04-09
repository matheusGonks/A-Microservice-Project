package com.shop_style.catalog_service.repositories;

import com.shop_style.catalog_service.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuRepository extends JpaRepository<Sku, Long> {
}

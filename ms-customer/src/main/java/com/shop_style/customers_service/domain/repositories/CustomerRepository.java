package com.shop_style.customers_service.domain.repositories;

import com.shop_style.customers_service.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> { }

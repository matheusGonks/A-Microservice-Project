package com.shop_style.customers_service.domain.repositories;

import com.shop_style.customers_service.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    public Set<Address> findByCustomerId(int customerId);

}

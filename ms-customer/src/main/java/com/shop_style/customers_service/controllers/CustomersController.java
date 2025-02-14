package com.shop_style.customers_service.controllers;

import com.shop_style.customers_service.domain.dtos.CustomerDTO;
import com.shop_style.customers_service.domain.facade.CustomerFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("v1/customers")
public class CustomersController {

    private final CustomerFacade customersFacade;

    CustomersController(CustomerFacade customersFacade){
        this.customersFacade = customersFacade;
    }

    @GetMapping
    public ResponseEntity<Set<CustomerDTO>> getAllCustomers(){
        Set<CustomerDTO> allCustomers = customersFacade.getAllCustomers();
        return (allCustomers.isEmpty())? ResponseEntity.noContent().build() : ResponseEntity.ok(allCustomers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable int id){
        CustomerDTO customer = customersFacade.getCustomer(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> postCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO createdCustomer = customersFacade.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> putCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable int id){
        CustomerDTO updatedCustomer = customersFacade.updateCustomer(id, customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<CustomerDTO> putCustomerPassword(@PathVariable int id, @RequestBody String password) {
        customersFacade.updateCustomerPassword(id, password);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable int id){
        CustomerDTO removedCustomer = customersFacade.removeCustomer(id);
        return ResponseEntity.ok(removedCustomer);
    }
}

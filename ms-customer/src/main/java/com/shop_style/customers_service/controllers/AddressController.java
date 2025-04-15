package com.shop_style.customers_service.controllers;

import com.shop_style.customers_service.domain.dtos.AddressDTO;

import com.shop_style.customers_service.domain.facade.CustomerFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("v1/addresses")
public class AddressController {

    CustomerFacade customerFacade;

    public AddressController(CustomerFacade customerFacade){
        this.customerFacade = customerFacade;
    }

    @PostMapping
    public ResponseEntity<AddressDTO> postAddress(@RequestBody @Valid AddressDTO addressDTO){
        AddressDTO addressCreated = customerFacade.createAddress(addressDTO);
        return new ResponseEntity<>(addressCreated, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> putAddress(@PathVariable int id, @RequestBody @Valid AddressDTO updates){
        AddressDTO updatedAddress = customerFacade.updateAddress(id, updates);
        return new ResponseEntity<>(updatedAddress, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressDTO> deleteAddress(@PathVariable int id){
        AddressDTO removedAddress = customerFacade.removeAddress(id);
        return new ResponseEntity<>(removedAddress, OK);
    }

}

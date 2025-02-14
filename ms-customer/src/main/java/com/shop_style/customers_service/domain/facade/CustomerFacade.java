package com.shop_style.customers_service.domain.facade;

import com.shop_style.customers_service.domain.dtos.AddressDTO;
import com.shop_style.customers_service.domain.dtos.CustomerDTO;
import com.shop_style.customers_service.domain.model.Address;
import com.shop_style.customers_service.domain.model.Customer;
import com.shop_style.customers_service.domain.services.AddressService;
import com.shop_style.customers_service.domain.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerFacade {

    private final CustomerService customerService;

    private final AddressService addressService;

    CustomerFacade(CustomerService customerService, AddressService addressService){
        this.customerService = customerService;
        this.addressService = addressService;
    }

    public CustomerDTO getCustomer(int id){
        Customer retrieved = customerService.getCustomer(id);
        setCustomerAddresses(retrieved);
        return DtoConverter.makeDtoFromCustomer(retrieved);
    }

    public CustomerDTO createCustomer(CustomerDTO customer){
        Customer newCustomer = DtoConverter.makeCustomerFromDto(customer);
        Customer savedVersion = customerService.createCustomer(newCustomer);
        return DtoConverter.makeDtoFromCustomer(savedVersion);
    }

    public CustomerDTO updateCustomer(int id, CustomerDTO customer){
        Customer customerUpdates = DtoConverter.makeCustomerFromDto(customer);
        Customer updatedVersion = customerService.updateCustomer(id, customerUpdates);
        return DtoConverter.makeDtoFromCustomer(updatedVersion);
    }

    public void updateCustomerPassword(int id, String password){

    }

    public CustomerDTO removeCustomer(int id){
        Customer deleted = customerService.removeCustomer(id);
        return DtoConverter.makeDtoFromCustomer(deleted);
    }

    public Set<CustomerDTO> getAllCustomers(){
        return customerService
                .getAllCustomers()
                .stream()
                .peek(this::setCustomerAddresses)
                .map(DtoConverter::buildDtoFromConcreteCustomer)
                .collect(Collectors.toSet());
    }

    private void setCustomerAddresses(Customer customer){
        Set<Address> addresses = addressService.getAllAddressesByCustomer(customer.getId());
        customer.setAddresses(addresses);
    }

    public AddressDTO createAddress(AddressDTO addressDTO){
        Address address = DtoConverter.makeAddressFromDto(addressDTO);
        Address createdAddress = addressService.createAddress(address, addressDTO.getCustomerId());
        return DtoConverter.makeDtoFromAddress(createdAddress);
    }

    public AddressDTO updateAddress(int id, AddressDTO addressUpdates){
        Address updates = DtoConverter.makeAddressFromDto(addressUpdates);
        Address updatedAddress = addressService.updateAddress(updates, id);
        return DtoConverter.makeDtoFromAddress(updatedAddress);
    }

    public AddressDTO removeAddress(int id){
        Address removedAddress = addressService.removeAddress(id);
        return DtoConverter.makeDtoFromAddress(removedAddress);
    }
}

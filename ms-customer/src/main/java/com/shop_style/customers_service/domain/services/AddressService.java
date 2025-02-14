package com.shop_style.customers_service.domain.services;

import com.shop_style.customers_service.domain.model.Address;
import com.shop_style.customers_service.domain.model.Customer;
import com.shop_style.customers_service.domain.repositories.AddressRepository;
import com.shop_style.customers_service.exceptions.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerService customerService;

    public AddressService(AddressRepository addressRepository, CustomerService customerService){
        this.addressRepository = addressRepository;
        this.customerService = customerService;
    }

    public Address createAddress(Address address, int addressOwnerId){
        Customer addressOwner = customerService.getCustomer(addressOwnerId);
        address.setCustomer(addressOwner);
        return addressRepository.save(address);
    }

    public Address updateAddress(Address updates, int addressId){
        Address addressToBeUpdated = getAddress(addressId);
        updateAddressAttributes(addressToBeUpdated, updates);
        return addressRepository.save(addressToBeUpdated);
    }

    public Address removeAddress(int id){
        Address removedAddress = getAddress(id);
        addressRepository.delete(removedAddress);
        return removedAddress;
    }

    private Address getAddress(int id){
        return addressRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(Address.class, id));
    }

    public Set<Address> getAllAddressesByCustomer(int customerId){
        return addressRepository.findByCustomerId(customerId);
    }

    private void updateAddressAttributes(Address toBeUpdated, Address updates){
        toBeUpdated.setState(updates.getState());
        toBeUpdated.setCity(updates.getCity());
        toBeUpdated.setDistrict(updates.getDistrict());
        toBeUpdated.setNumber(updates.getNumber());
        toBeUpdated.setStreet(updates.getStreet());
        toBeUpdated.setZipcode(updates.getZipcode());
        toBeUpdated.setComplement(updates.getComplement());
    }
}

package com.shop_style.customers_service.domain.services;

import com.shop_style.customers_service.DummyAddressBuilder;
import com.shop_style.customers_service.DummyCustomerBuilder;
import com.shop_style.customers_service.domain.model.Address;
import com.shop_style.customers_service.domain.model.Customer;
import com.shop_style.customers_service.domain.repositories.AddressRepository;
import com.shop_style.customers_service.exceptions.ResourceNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AddressService addressService;

    private Customer dummyCustomer;

    private Address dummyAddress;

    private Address dummyAddressUpdates;

    private DummyCustomerBuilder dummyCustomerBuilder;

    private DummyAddressBuilder dummyAddressBuilder;

    private final int DEFAULT_CUSTOMER_ID_FOR_TEST = 1;

    private final int DEFAULT_ADDRESS_ID_FOR_TEST = 11;

    @BeforeEach
    public void setUp(){
        dummyCustomerBuilder = new DummyCustomerBuilder();
        dummyAddressBuilder = new DummyAddressBuilder();
        setDummies();
    }

    @Test
    @DisplayName("Address Service - Successful get all Addresses")
    public void addressServiceShouldReturnListWithAddresses(){
        when(addressRepository.findByCustomerId(DEFAULT_CUSTOMER_ID_FOR_TEST)).thenReturn(Set.of(dummyAddress));

        Set<Address> allAddressesByCustomer = addressService.getAllAddressesByCustomer(DEFAULT_CUSTOMER_ID_FOR_TEST);
        assertEquals(1, allAddressesByCustomer.size(), "Didn't build list properly.");

        verify(addressRepository, times(1)).findByCustomerId(DEFAULT_CUSTOMER_ID_FOR_TEST);
    }

    @Test
    @DisplayName("Address Service - successful creation of address")
    public void addressServiceShouldCreateNewAddress(){
        when(customerService.getCustomer(DEFAULT_CUSTOMER_ID_FOR_TEST)).thenReturn(dummyCustomer);
        when(addressRepository.save(dummyAddress)).thenReturn(dummyAddress);

        Address savedAddress = addressService.createAddress(dummyAddress, DEFAULT_CUSTOMER_ID_FOR_TEST);
        assertEquals(savedAddress.getCustomer(), dummyCustomer, "Address customer not set properly.");

        verify(customerService, times(1)).getCustomer(DEFAULT_CUSTOMER_ID_FOR_TEST);
        verify(addressRepository, times(1)).save(dummyAddress);
    }

    @Test
    @DisplayName("Address Service - unsuccessful creation of address")
    public void addressServiceShouldNotCreateNewAddressBecauseOfCustomerId(){
        when(customerService.getCustomer(DEFAULT_CUSTOMER_ID_FOR_TEST)).thenThrow(new ResourceNotFound(Customer.class, DEFAULT_CUSTOMER_ID_FOR_TEST));

        assertThrows(ResourceNotFound.class,
                () -> addressService.createAddress(dummyAddress, DEFAULT_CUSTOMER_ID_FOR_TEST),
                "Didn't throw expected error.");

        verify(customerService, times(1)).getCustomer(DEFAULT_CUSTOMER_ID_FOR_TEST);
        verify(addressRepository, never()).save(any());
    }

    @Test
    @DisplayName("Address Service - successful update of address")
    public void addressServiceShouldUpdateAddressAttributes(){

        Address oldAddress = dummyAddressBuilder.buildConcrete();

        when(addressRepository.findById(DEFAULT_ADDRESS_ID_FOR_TEST)).thenReturn(Optional.of(dummyAddress));
        when(addressRepository.save(dummyAddress)).thenReturn(dummyAddress);

        Address savedAddress = addressService.updateAddress(dummyAddressUpdates, DEFAULT_ADDRESS_ID_FOR_TEST);

        assertAll(
                () -> assertEquals( oldAddress.getState(), savedAddress.getState(), "Field state changed but it shouldn't."),
                () -> assertEquals( oldAddress.getCity(), savedAddress.getCity(), "Field city changed but it shouldn't."),
                () -> assertEquals( oldAddress.getDistrict(), savedAddress.getDistrict(), "Field district changed but it shouldn't."),
                () -> assertEquals( oldAddress.getStreet(), savedAddress.getStreet(), "Field street changed but it shouldn't."),

                () -> assertNotEquals(oldAddress.getNumber(), savedAddress.getNumber(),  "Field was not updated."),
                () -> assertNotEquals(oldAddress.getZipcode(), savedAddress.getZipcode(), "Field was not updated."),
                () -> assertNotEquals(oldAddress.getComplement(), savedAddress.getComplement(), "Field was not updated.")
        );

        verify(addressRepository, times(1)).findById(DEFAULT_ADDRESS_ID_FOR_TEST);
        verify(addressRepository, times(1)).save(dummyAddress);
    }

    @Test
    @DisplayName("Address Service - unsuccessful update of address")
    public void addressServiceShouldNotUpdateAddressAttributesBecauseIdDoesNotExist(){
        when(addressRepository.findById(DEFAULT_ADDRESS_ID_FOR_TEST)).thenThrow(new ResourceNotFound(Address.class, DEFAULT_ADDRESS_ID_FOR_TEST));

        assertThrows(ResourceNotFound.class,
                () -> addressService.updateAddress(dummyAddressUpdates, DEFAULT_ADDRESS_ID_FOR_TEST),
                "Didn't throw expected error.");

        verify(addressRepository, times(1)).findById(DEFAULT_ADDRESS_ID_FOR_TEST);
        verify(addressRepository, never()).save(any());
    }

    @Test
    @DisplayName("Address Service - successful removal of Address")
    public void addressServiceShouldRemoveAddress(){
        when(addressRepository.findById(DEFAULT_ADDRESS_ID_FOR_TEST)).thenReturn(Optional.ofNullable(dummyAddress));

        Address removedAddress = addressService.removeAddress(DEFAULT_ADDRESS_ID_FOR_TEST);
        assertEquals(removedAddress, dummyAddress, "Didn't return removed address");

        verify(addressRepository, times(1)).findById(DEFAULT_ADDRESS_ID_FOR_TEST);
        verify(addressRepository, times(1)).delete(dummyAddress);
    }

    @Test
    @DisplayName("Address Service - unsuccessful removal of Address")
    public void addressServiceShouldNotRemoveAddress(){
        when(addressRepository.findById(DEFAULT_ADDRESS_ID_FOR_TEST))
                .thenThrow(new ResourceNotFound(Address.class, DEFAULT_ADDRESS_ID_FOR_TEST));

        assertThrows(ResourceNotFound.class, () -> addressService.removeAddress(DEFAULT_ADDRESS_ID_FOR_TEST));

        verify(addressRepository, times(1)).findById(DEFAULT_ADDRESS_ID_FOR_TEST);
        verify(addressRepository, never()).delete(dummyAddress);
    }

    private void setDummies(){
        this.dummyCustomer = dummyCustomerBuilder.buildConcrete();
        this.dummyAddress = dummyAddressBuilder.buildConcrete();
        this.dummyAddressUpdates = dummyAddressBuilder
                .setNumber("241")
                .setZipcode("37201-112")
                .setComplement("Perto do posto 2 irmãos")
                .buildConcrete();

        dummyAddressBuilder = new DummyAddressBuilder();
    }
}
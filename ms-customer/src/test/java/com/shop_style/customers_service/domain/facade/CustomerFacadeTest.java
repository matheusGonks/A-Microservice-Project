package com.shop_style.customers_service.domain.facade;

import com.shop_style.customers_service.DummyAddressBuilder;
import com.shop_style.customers_service.DummyCustomerBuilder;
import com.shop_style.customers_service.domain.dtos.AddressDTO;
import com.shop_style.customers_service.domain.dtos.CustomerDTO;
import com.shop_style.customers_service.domain.model.Address;
import com.shop_style.customers_service.domain.model.Customer;
import com.shop_style.customers_service.domain.services.AddressService;
import com.shop_style.customers_service.domain.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerFacadeTest {

    @InjectMocks
    CustomerFacade customersFacade;

    @Mock
    CustomerService customerService;

    @Mock
    AddressService addressService;

    DummyAddressBuilder dummyAddressBuilder;

    DummyCustomerBuilder dummyCustomerBuilder;

    Customer customerForTests;

    CustomerDTO customerDTOForTests;

    Address addressForTests;

    AddressDTO addressDTOForTests;

    private final int DEFAULT_ID_FOR_TESTING = 1;

    @BeforeEach
    void setup(){
        dummyCustomerBuilder = new DummyCustomerBuilder();
        dummyAddressBuilder = new DummyAddressBuilder();

        customerForTests = dummyCustomerBuilder.buildConcrete();
        customerDTOForTests = dummyCustomerBuilder.buildDto();
        addressForTests = dummyAddressBuilder.buildConcrete();
        addressDTOForTests = dummyAddressBuilder.buildDto();
    }

    @Test
    @DisplayName("Customer Facade - successful creation of Customer")
    public void customersFacadeShouldCreatedCustomer(){
        when(customerService.createCustomer(any())).thenReturn(customerForTests);
        CustomerDTO returnedCustomer = customersFacade.createCustomer(customerDTOForTests);
        assertEquals(customerDTOForTests, returnedCustomer);
    }

    @Test
    @DisplayName("Customer Facade - successful retrieval of Customer")
    public void customersFacadeShouldReturnRetrievedCustomer(){
        when(customerService.getCustomer(DEFAULT_ID_FOR_TESTING)).thenReturn(customerForTests);
        CustomerDTO retrievedCustomerDto = customersFacade.getCustomer(DEFAULT_ID_FOR_TESTING);
        assertEquals(customerDTOForTests, retrievedCustomerDto);
    }

    @Test
    @DisplayName("Customer Facade - successful removal of customer")
    public void customersFacadeShouldRemoveCustomer(){
        when(customerService.removeCustomer(DEFAULT_ID_FOR_TESTING)).thenReturn(customerForTests);
        CustomerDTO removedCustomerDto = customersFacade.removeCustomer(DEFAULT_ID_FOR_TESTING);
        assertEquals(customerDTOForTests, removedCustomerDto);
    }

    @Test
    @DisplayName("Customer Facade - successful update of Customer")
    public void customersFacadeShouldUpdateCustomer(){
        Customer customerUpdates = dummyCustomerBuilder.buildConcrete();
        CustomerDTO customerDTOUpdates = dummyCustomerBuilder.buildDto();
        when(customerService.updateCustomer(eq(DEFAULT_ID_FOR_TESTING), any() )).thenReturn(customerUpdates);

        CustomerDTO updatedCustomerDto = customersFacade.updateCustomer(DEFAULT_ID_FOR_TESTING, customerDTOUpdates);
        assertEquals(updatedCustomerDto, customerDTOUpdates);
    }

    @Test
    @DisplayName("Customer Facade - successful update of Address")
    public void customerFacadeShouldUpdateAddress(){
        when(addressService.updateAddress(any(), eq(DEFAULT_ID_FOR_TESTING))).thenReturn(addressForTests);
        AddressDTO updatedAddress = customersFacade.updateAddress(DEFAULT_ID_FOR_TESTING, addressDTOForTests);
        assertEquals(addressDTOForTests, updatedAddress);
    }

    @Test
    @DisplayName("Customer Facade - successful creation of Address")
    public void customerFacadeShouldCreateAddress(){
        when(addressService.createAddress(any(), eq(DEFAULT_ID_FOR_TESTING))).thenReturn(addressForTests);
        AddressDTO createdAddress = customersFacade.createAddress(addressDTOForTests);
        assertEquals(addressDTOForTests, createdAddress);
    }

    @Test
    @DisplayName("Customer Facade - successful removal of Address")
    public void customerFacadeShouldRemoveAddress(){
        when(addressService.removeAddress(DEFAULT_ID_FOR_TESTING)).thenReturn(addressForTests);
        AddressDTO removedAddress = customersFacade.removeAddress(DEFAULT_ID_FOR_TESTING);
        assertEquals(addressDTOForTests, removedAddress);
    }
}
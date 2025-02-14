package com.shop_style.customers_service.domain.services;

import com.shop_style.customers_service.DummyCustomerBuilder;
import com.shop_style.customers_service.domain.enums.Gender;
import com.shop_style.customers_service.domain.model.Customer;
import com.shop_style.customers_service.domain.repositories.CustomerRepository;
import com.shop_style.customers_service.exceptions.ResourceNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressService addressService;

    private Customer dummyCustomer1;

    private Customer dummyCustomer2;

    private Customer dummyCustomer1Updates;

    private DummyCustomerBuilder dummyCustomerBuilder;

    private final int DEFAULT_CUSTOMER_ID_FOR_TESTING = 1;

    @BeforeEach
    public void setup(){
        dummyCustomerBuilder = new DummyCustomerBuilder();
        setDummies();
    }

    @Test
    @DisplayName("CustomerService - successful retrieval by id")
    public void customerServiceReturnsCustomerWithProvidedId(){
        when(customerRepository.findById(DEFAULT_CUSTOMER_ID_FOR_TESTING)).thenReturn(Optional.ofNullable(dummyCustomer1));

        Customer customer = customerService.getCustomer(DEFAULT_CUSTOMER_ID_FOR_TESTING);
        assertEquals(customer, dummyCustomer1);

        verify(customerRepository).findById(DEFAULT_CUSTOMER_ID_FOR_TESTING);
    }

    @Test
    @DisplayName("CustomerService - non-successful retrieval by Id")
    public void customerServiceReturnsNoCustomerAndThrowsException(){
        when(customerRepository.findById(DEFAULT_CUSTOMER_ID_FOR_TESTING)).thenThrow(new ResourceNotFound(Customer.class, DEFAULT_CUSTOMER_ID_FOR_TESTING));
        assertThrows(ResourceNotFound.class, () -> customerService.getCustomer(DEFAULT_CUSTOMER_ID_FOR_TESTING));

        verify(customerRepository).findById(DEFAULT_CUSTOMER_ID_FOR_TESTING);
    }

    @Test
    @DisplayName("CustomerService - successful retrieval of multiple customers.")
    public void customerServiceReturnsCustomersList(){
        when(customerRepository.findAll()).thenReturn(List.of(dummyCustomer1,dummyCustomer2));
        Set<Customer> retrievedCustomers = customerService.getAllCustomers();
        assertAll (
                () -> assertTrue(retrievedCustomers.contains(dummyCustomer1),"Returned set does not contain dummy1."),
                () -> assertTrue(retrievedCustomers.contains(dummyCustomer2),"Returned set does not contain dummy2."),
                () -> assertFalse(retrievedCustomers.isEmpty(), "Returned List was empty.")
        );
    }

    @Test
    @DisplayName("CustomerService - successful creation of customer.")
    public void customerServiceUpdatesCustomerSuccessfully(){
        when(customerRepository.save(dummyCustomer1)).thenReturn(dummyCustomer1);
        customerService.createCustomer(dummyCustomer1);
        verify(customerRepository).save(dummyCustomer1);
    }

    @Test
    @DisplayName("Customer Service - successful update.")
    public void customerServiceUpdatesCustomer(){

        String oldEmail = dummyCustomer1.getEmail();
        Gender oldGender = dummyCustomer1.getGender();
        String oldLastName = dummyCustomer1.getLastName();

        when(customerRepository.findById(DEFAULT_CUSTOMER_ID_FOR_TESTING)).thenReturn(Optional.ofNullable(dummyCustomer1));
        when(customerRepository.save(any())).thenReturn(dummyCustomer1);

        Customer updatedCustomer = customerService.updateCustomer(DEFAULT_CUSTOMER_ID_FOR_TESTING, dummyCustomer1Updates);

        assertAll(
                () -> assertEquals(updatedCustomer.getEmail(), dummyCustomer1Updates.getEmail()),
                () -> assertNotEquals(updatedCustomer.getEmail(), oldEmail),

                () -> assertEquals(updatedCustomer.getGender(), dummyCustomer1Updates.getGender()),
                () -> assertNotEquals(updatedCustomer.getGender(), oldGender),

                () -> assertEquals(updatedCustomer.getLastName(), dummyCustomer1Updates.getLastName()),
                () -> assertNotEquals(updatedCustomer.getLastName(), oldLastName)
        );

        verify(customerRepository).findById(DEFAULT_CUSTOMER_ID_FOR_TESTING);
        verify(customerRepository).save(any());
    }

    @Test
    @DisplayName("Customer Service - non-successful update.")
    public void customerServiceUpdateOfNonExistentCustomerThrowsExceptio() {
        when(customerRepository.findById(DEFAULT_CUSTOMER_ID_FOR_TESTING)).thenThrow(new ResourceNotFound(Customer.class, DEFAULT_CUSTOMER_ID_FOR_TESTING));
        assertThrows(ResourceNotFound.class,
                () -> customerService.updateCustomer(DEFAULT_CUSTOMER_ID_FOR_TESTING, dummyCustomer1Updates), "Expected exception not thrown.");

        verify(customerRepository).findById(DEFAULT_CUSTOMER_ID_FOR_TESTING);
    }

    @Test
    @DisplayName("Customer Service - successful removal by Id.")
    public  void customerServiceRemovalOfCustomer(){
        when(customerRepository.findById(DEFAULT_CUSTOMER_ID_FOR_TESTING)).thenReturn(Optional.ofNullable(dummyCustomer1));
        Customer removedCustomer = customerService.removeCustomer(DEFAULT_CUSTOMER_ID_FOR_TESTING);
        assertEquals(removedCustomer, dummyCustomer1);

        verify(customerRepository, times(1)).findById(DEFAULT_CUSTOMER_ID_FOR_TESTING);
        verify(customerRepository).delete(dummyCustomer1);
    }

    @Test
    @DisplayName("Customer Service - unsuccessful removal by Id.")
    public  void customerServiceCallsRemovalOfCustomerUnsuccessful(){
        when(customerRepository.findById(DEFAULT_CUSTOMER_ID_FOR_TESTING)).thenThrow(new ResourceNotFound(Customer.class, DEFAULT_CUSTOMER_ID_FOR_TESTING));
        assertThrows(ResourceNotFound.class, () -> customerService.removeCustomer(DEFAULT_CUSTOMER_ID_FOR_TESTING));
        verify(customerRepository, never()).delete(dummyCustomer1);
    }

    private void setDummies(){

        dummyCustomer1 = dummyCustomerBuilder.buildConcrete();

        dummyCustomer1Updates = dummyCustomerBuilder.setLastName("Pasquim Valadares")
                .setGender(Gender.NON_BINARY)
                .setEmail("marcosValadares@gmail.com")
                .buildConcrete();

        dummyCustomer2 = dummyCustomerBuilder
                .setCpf("101.202.303-11")
                .setFirstName("Juliana")
                .setLastName("Mesquita")
                .setGender(Gender.FEMALE)
                .setBirthdate(LocalDate.of(1991, Month.MARCH, 31))
                .setEmail("ju_mes8h@gmail.com")
                .setPassword("ASKO_lçk212")
                .buildConcrete();

    }
}
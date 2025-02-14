package com.shop_style.customers_service.domain.facade;

import com.shop_style.customers_service.DummyAddressBuilder;
import com.shop_style.customers_service.DummyCustomerBuilder;
import com.shop_style.customers_service.domain.dtos.AddressDTO;
import com.shop_style.customers_service.domain.dtos.CustomerDTO;
import com.shop_style.customers_service.domain.enums.Gender;
import com.shop_style.customers_service.domain.model.Address;
import com.shop_style.customers_service.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DtoConverterTest {

    private CustomerDTO customerDtoForTest;

    private Customer concreteCustomerForTest;

    private Address concreteAddressForTest;

    private AddressDTO addressDtoForTest;

    @BeforeEach
    public void setUp(){
        DummyCustomerBuilder dummyCustomerBuilder = new DummyCustomerBuilder();
        DummyAddressBuilder dummyAddressBuilder = new DummyAddressBuilder();

        this.concreteAddressForTest = dummyAddressBuilder.buildConcrete();
        this.addressDtoForTest = dummyAddressBuilder.buildDto();
        this.customerDtoForTest = dummyCustomerBuilder.buildDto();
        this.concreteCustomerForTest = dummyCustomerBuilder.buildConcrete();

        concreteCustomerForTest.setAddresses(Set.of(concreteAddressForTest));
    }

    @Test
    @DisplayName("DtoFactory - Create Customer from CustomerDto")
    public void dtoConverterShouldCreateCustomerFromDto(){

        Customer concreteCustomer = DtoConverter.makeCustomerFromDto(customerDtoForTest);

        assertEquals(Gender.MALE, concreteCustomer.getGender(), "Gender was not properly converted");
        assertAll(
                () -> assertEquals(21,
                        concreteCustomer.getBirthdate().getDayOfMonth(),
                        "Local date was not properly converted for day"),

                () -> assertEquals(Month.DECEMBER,
                        concreteCustomer.getBirthdate().getMonth(),
                        "Local date was not properly converted for month"),

                () -> assertEquals(2001,
                        concreteCustomer.getBirthdate().getYear(),
                        "Local date was not properly converted for day")
        );
    }

    @Test
    @DisplayName("DtoConverter - Created Address from Dto")
    public void dtoConverterShouldCreateAddressFromDto(){
        Address convertedAddress = DtoConverter.makeAddressFromDto(addressDtoForTest);
        assertEquals(convertedAddress, concreteAddressForTest, "Didn't convert dto to address properly." );
    }

    @Test
    @DisplayName("DtoConverter - Created DTO from Address")
    public void dtoConverterShouldCreateDtoFromAddress(){
        AddressDTO convertedDto = DtoConverter.makeDtoFromAddress(concreteAddressForTest);
        assertEquals(convertedDto, addressDtoForTest, convertedDto.getState() + " " + addressDtoForTest.getState());
    }

    @Test
    @DisplayName("DtoConverter - Created DTO from Customer")
    public void dtoConverterShouldCreateDTOFromConcreteCustomer(){
        CustomerDTO convertedDTO = DtoConverter.makeDtoFromCustomer(concreteCustomerForTest);
        assertEquals(convertedDTO, customerDtoForTest, "Did not convert customer to Dto properly.");
    }
}
package com.shop_style.customers_service.domain.facade;

import com.shop_style.customers_service.domain.dtos.AddressDTO;
import com.shop_style.customers_service.domain.dtos.CustomerDTO;
import com.shop_style.customers_service.domain.enums.Gender;
import com.shop_style.customers_service.domain.enums.State;
import com.shop_style.customers_service.domain.model.Address;
import com.shop_style.customers_service.domain.model.Customer;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class DtoConverter {

    private static final DateTimeFormatter VALID_FORMAT_1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter VALID_FORMAT_2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static Customer makeCustomerFromDto(CustomerDTO customerDTO){
        return buildConcreteCustomerFromDto(customerDTO);
    }

    public static Address makeAddressFromDto(AddressDTO addressDTO){
        return buildConcreteAddressFromDto(addressDTO);
    }

    public static AddressDTO makeDtoFromAddress(Address address){
        return buildDtoFromConcreteAddress(address);
    }

    public static CustomerDTO makeDtoFromCustomer(Customer customer){
        return buildDtoFromConcreteCustomer(customer);
    }

    private static Customer buildConcreteCustomerFromDto(CustomerDTO customerDTO){
        return new Customer(
                customerDTO.getCpf(),
                customerDTO.getFirstName(),
                customerDTO.getLastName(),
                parseGender(customerDTO.getGender()),
                parseLocalDate(customerDTO.getBirthdate()),
                customerDTO.getEmail(),
                customerDTO.getBirthdate(),
                customerDTO.getActive());
    }


    public static CustomerDTO buildDtoFromConcreteCustomer(Customer customer){
        Set<AddressDTO> addressesDtos = extractAddressesFromCustomerAsDtos(customer);

        return new CustomerDTO(
                customer.getId(),
                customer.getCpf(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getGender().getFormattedName(),
                customer.getBirthdate().format(VALID_FORMAT_2),
                customer.getEmail(),
                customer.isActive(),
                addressesDtos);
    }

    private static Set<AddressDTO> extractAddressesFromCustomerAsDtos(Customer customer){
        Set<Address> addresses = customer.getAddresses();
        return addresses
                .stream()
                .map(DtoConverter::buildDtoFromConcreteAddress)
                .collect(Collectors.toSet());
    }

    public static AddressDTO buildDtoFromConcreteAddress(Address address){
        return new AddressDTO(
            address.getId(),
            address.getState().getFullName(),
            address.getCity(),
            address.getDistrict(),
            address.getStreet(),
            address.getNumber(),
            address.getZipcode(),
            address.getComplement()
        );
    }

    public static Address buildConcreteAddressFromDto(AddressDTO addressDTO){
        return new Address(
            parseState(addressDTO.getState()),
            addressDTO.getCity(),
            addressDTO.getDistrict(),
            addressDTO.getStreet(),
            addressDTO.getNumber(),
            addressDTO.getZipcode(),
            addressDTO.getComplement()
        );
    }

    private static LocalDate parseLocalDate(String birthdate){
        try {
            return  LocalDate.parse(birthdate, VALID_FORMAT_1);
        }catch (DateTimeException e){
            return  LocalDate.parse(birthdate, VALID_FORMAT_2);
        }
    }

    private static Gender parseGender(String gender){
        return Gender.valueOf(gender.toUpperCase().replace("-", "_"));
    }

    private static State parseState(String stateString){
        return Arrays
                .stream(State.values())
                .filter((state) -> stateString.equalsIgnoreCase(state.name()) || stateString.equalsIgnoreCase(state.getFullName()))
                .findFirst()
                .orElseThrow();
    }
}

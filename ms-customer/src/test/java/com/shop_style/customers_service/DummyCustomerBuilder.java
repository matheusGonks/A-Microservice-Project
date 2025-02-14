package com.shop_style.customers_service;

import com.shop_style.customers_service.domain.dtos.AddressDTO;
import com.shop_style.customers_service.domain.dtos.CustomerDTO;
import com.shop_style.customers_service.domain.enums.Gender;
import com.shop_style.customers_service.domain.model.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class DummyCustomerBuilder {

    private int id;

    private String cpf = "134.459.556-11";

    private String firstName = "Matheus";

    private String lastName = "Gonçalves";

    private LocalDate birthdate = LocalDate.of(2001, 12, 21);

    private String dtoBirthdate = birthdate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    private Gender gender = Gender.MALE;

    private String dtoGender = gender.getFormattedName();

    private String email = "matheuzin@gmail.com";

    private String password = "UmaSenha123";

    private boolean active = true;

    private Set<AddressDTO> addresses = new HashSet<>();

    public Customer buildConcrete(){
        return new Customer(
                this.cpf,
                this.firstName,
                this.lastName,
                this.gender,
                this.birthdate,
                this.email,
                this.password,
                this.active
        );
    }

    public CustomerDTO buildDto(){
        return new CustomerDTO(
                this.id,
                this.cpf,
                this.firstName,
                this.lastName,
                this.dtoGender,
                this.dtoBirthdate,
                this.email,
                this.active,
                this.addresses
        );
    }

    public DummyCustomerBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public DummyCustomerBuilder setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public DummyCustomerBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public DummyCustomerBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public DummyCustomerBuilder setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        this.dtoBirthdate = birthdate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return this;
    }

    public DummyCustomerBuilder setGender(Gender gender) {
        this.gender = gender;
        this.dtoGender = gender.getFormattedName();
        return this;
    }

    public DummyCustomerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public DummyCustomerBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public DummyCustomerBuilder setActive(boolean active) {
        this.active = active;
        return this;
    }

    public DummyCustomerBuilder setAddresses(Set<AddressDTO> addresses) {
        this.addresses = addresses;
        return this;
    }
}

package com.shop_style.customers_service;

import com.shop_style.customers_service.domain.dtos.AddressDTO;
import com.shop_style.customers_service.domain.enums.State;
import com.shop_style.customers_service.domain.model.Address;

public class DummyAddressBuilder {

    private int id = 1;

    private State state = State.MG;

    private String dtoState = state.getFullName();

    private String city = "Lavras";

    private String district = "Meu distrito";

    private String street = "Rua Getúlio Vargas";

    private String number = "12A";

    private String zipcode = "37890-900";

    private String complement = "Perto do Parque";

    private int customerId = 1;

    public Address buildConcrete(){
        return new Address(
          this.state,
          this.city,
          this.district,
          this.street,
          this.number,
          this.zipcode,
          this.complement
        );
    }

    public AddressDTO buildDto(){
        AddressDTO addressDTO = new AddressDTO(
                this.id,
                this.dtoState,
                this.city,
                this.district,
                this.street,
                this.number,
                this.zipcode,
                this.complement
        );

        addressDTO.setCustomerId(this.customerId);
        return addressDTO;
    }

    public DummyAddressBuilder setId(int id){
        this.id = id;
        return this;
    }

    public DummyAddressBuilder setState(State state){
        this.state = state;
        this.dtoState = state.getFullName();
        return this;
    }

    public DummyAddressBuilder setCity(String city){
        this.city = city;
        return this;
    }

    public DummyAddressBuilder setDistrict(String district){
        this.district = district;
        return this;
    }

    public DummyAddressBuilder setStreet(String street){
        this.street = street;
        return this;
    }

    public DummyAddressBuilder setNumber(String number){
        this.number = number;
        return this;
    }

    public DummyAddressBuilder setZipcode(String zipcode){
        this.zipcode = zipcode;
        return this;
    }

    public DummyAddressBuilder setComplement(String complement){
        this.complement = complement;
        return this;
    }

    public DummyAddressBuilder customerId(int customerId){
        this.customerId = customerId;
        return this;
    }
}

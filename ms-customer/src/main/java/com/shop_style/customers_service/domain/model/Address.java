package com.shop_style.customers_service.domain.model;

import com.shop_style.customers_service.domain.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private State state;

    private String city;

    private String district;

    private String street;

    private String number;

    private String zipcode;

    private String complement;

    @ManyToOne
    private Customer customer;

    public Address(State state, String city, String district, String street, String number, String zipcode, String complement) {
        this.state = state;
        this.city = city;
        this.district = district;
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.complement = complement;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Address address)) return false;
        return state == address.state &&
                Objects.equals(city, address.city) &&
                Objects.equals(district, address.district) &&
                Objects.equals(street, address.street) &&
                Objects.equals(number, address.number) &&
                Objects.equals(zipcode, address.zipcode) &&
                Objects.equals(complement, address.complement) &&
                Objects.equals(customer, address.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, city, district, street, number, zipcode, complement);
    }
}

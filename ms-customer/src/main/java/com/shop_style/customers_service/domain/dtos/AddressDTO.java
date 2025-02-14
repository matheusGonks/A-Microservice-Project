package com.shop_style.customers_service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop_style.customers_service.domain.validations.ValidState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotBlank(message = "User should provide a state.")
    @ValidState
    private String state;

    @NotBlank(message = "User should provide a valid city.")
    private String city;

    @NotBlank(message = "User should provide a valid district.")
    private String district;

    @NotBlank(message = "User should provide a valid street.")
    private String street;

    @NotBlank(message = "User should provide a valid number.")
    private String number;

    @NotBlank(message = "User should provide a valid zipcode.")
    @Pattern(regexp = "[0-9]{5}-[0-9]{3}", message = "User should provide a valid zipcode: xxxxx-xxx.")
    private String zipcode;

    private String complement;

    @NotNull(message = "User should provide a valid owner customer.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer customerId;

    public AddressDTO(Integer id, String state, String city, String district, String street, String number, String zipcode, String complement) {
        this.id = id;
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
        if (!(o instanceof AddressDTO that)) return false;
        return  Objects.equals(state, that.state) && Objects.equals(city, that.city) && Objects.equals(district, that.district) && Objects.equals(street, that.street) && Objects.equals(number, that.number) && Objects.equals(zipcode, that.zipcode) && Objects.equals(complement, that.complement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, city, district, street, number, zipcode, complement, customerId);
    }
}

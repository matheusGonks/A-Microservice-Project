package com.shop_style.customers_service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop_style.customers_service.domain.validations.ValidBirthdate;
import com.shop_style.customers_service.domain.validations.ValidGender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @CPF(message = "User should provide valid CPF.")
    @NotBlank(message = "A CPF should be provided.")
    private String cpf;

    @Pattern(regexp = "[\\p{L}]{3,}", message = "First name should be at least 3 characters long and have only letters.")
    @NotBlank(message = "Should be provided a first name.")
    private String firstName;

    @Pattern(regexp = "\\p{L}{3,}", message = "Last name should be at least 3 characters long and have only letters.")
    @NotBlank(message = "Should be provided a last name.")
    private String lastName;

    @NotBlank(message = "Gender should be provided.")
    @ValidGender
    private String gender;

    @NotBlank(message = "A birthdate should be provided")
    @ValidBirthdate
    private String birthdate;

    @Email(message = "Email should be provided in a correct format.")
    @NotBlank(message = "A valid email should be provided.")
    private String email;

    @NotBlank(message = "Customer must have a password.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "")
    private Boolean active = true;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Set<AddressDTO> addresses;

    public CustomerDTO(Integer id, String cpf, String firstName, String lastName, String gender, String birthdate, String email, Boolean active, Set<AddressDTO> addresses) {
        this.id = id;
        this.cpf = cpf;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.active = active;
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CustomerDTO that)) return false;
        return Objects.equals(cpf, that.cpf) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(gender, that.gender) && Objects.equals(birthdate, that.birthdate) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(active, that.active) && Objects.equals(addresses, that.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, firstName, lastName, gender, birthdate, email, password, active, addresses);
    }
}

package com.shop_style.customers_service.domain.model;

import com.shop_style.customers_service.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String cpf;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthdate;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean active;

    @OneToMany(mappedBy="customer")
    private Set<Address> addresses;

    public Customer(String cpf, String firstName, String lastName, Gender gender, LocalDate birthdate, String email, String password, boolean active) {
        this.cpf = cpf;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.active = active;
        this.addresses = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customer customer)) return false;
        return id == customer.id && active == customer.active && Objects.equals(cpf, customer.cpf) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && gender == customer.gender && Objects.equals(birthdate, customer.birthdate) && Objects.equals(email, customer.email) && Objects.equals(password, customer.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, firstName, lastName, gender, birthdate, email, password, active);
    }
}

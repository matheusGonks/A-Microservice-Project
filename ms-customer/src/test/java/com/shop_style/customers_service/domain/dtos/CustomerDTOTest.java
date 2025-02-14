package com.shop_style.customers_service.domain.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.logging.log4j.util.TriConsumer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDTOTest {

    private static Validator validator;
    private CustomerDTO customerDTO;

    private final TriConsumer<String, BiConsumer<CustomerDTO, String>, String> invalidFieldValidator = (expectedValidationMessage, fieldSetterMethod, setterArgument) -> {
        fieldSetterMethod.accept(customerDTO, setterArgument);
        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customerDTO);
        assertTrue(violations.stream().anyMatch(
                        violation -> violation.getMessage().equals(expectedValidationMessage)),
                violations.toString()
        );
    };

    private final BiConsumer<BiConsumer<CustomerDTO, String>, String> validFieldValidator = (fieldSetterMethod, fieldSetterArgument) -> {
        fieldSetterMethod.accept(customerDTO, fieldSetterArgument);
        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customerDTO);
        assertTrue(violations.isEmpty(), violations.toString());
    };

    @BeforeAll
    public static void setUpValidator(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setup(){
        customerDTO = new CustomerDTO();
        customerDTO.setCpf("134.459.556-11");
        customerDTO.setFirstName("Matheus");
        customerDTO.setLastName("Goncalves");
        customerDTO.setBirthdate("21-12-2001");
        customerDTO.setGender("Male");
        customerDTO.setEmail("matheuzin@gmail.com");
        customerDTO.setPassword("UmaSenha123");
        customerDTO.setActive(true);
    }

    @Test
    @DisplayName("CustomerDto Validation - validation of invalid CPF.")
    void testValidationForCPF(){

        final String INVALID_CPF_MESSAGE = "User should provide valid CPF.";
        final String NO_CPF_PROVIDED_MESSAGE = "A CPF should be provided.";

        assertAll(
                () -> validFieldValidator.accept(CustomerDTO::setCpf, "134.459.556-11"),
                () -> invalidFieldValidator.accept(INVALID_CPF_MESSAGE, CustomerDTO::setCpf,"12345678910"),
                () -> invalidFieldValidator.accept(INVALID_CPF_MESSAGE, CustomerDTO::setCpf,"123.456.789-10"),
                () -> invalidFieldValidator.accept(INVALID_CPF_MESSAGE, CustomerDTO::setCpf,"123456.789-10"),
                () -> invalidFieldValidator.accept(NO_CPF_PROVIDED_MESSAGE, CustomerDTO::setCpf,"")
        );
    }

    @Test
    @DisplayName("CustomerDto Validation - validation of invalid name.")
    void testValidationForFirstName(){

        final String INVALID_FIRST_NAME_MESSAGE = "First name should be at least 3 characters long and have only letters.";
        final String NO_FIRST_NAME_PROVIDED_MESSAGE = "Should be provided a first name.";

        assertAll(
                () -> validFieldValidator.accept(CustomerDTO::setFirstName, "Matheus"),
                () -> invalidFieldValidator.accept(INVALID_FIRST_NAME_MESSAGE, CustomerDTO::setFirstName,"Ma"),
                () -> invalidFieldValidator.accept(INVALID_FIRST_NAME_MESSAGE, CustomerDTO::setFirstName,"Mat234"),
                () -> invalidFieldValidator.accept(NO_FIRST_NAME_PROVIDED_MESSAGE, CustomerDTO::setFirstName,"")
        );
    }

    @Test
    @DisplayName("CustomerDto Validation - validation of invalid last Name.")
    void testValidationForLastName(){

        final String INVALID_LAST_NAME_MESSAGE = "Last name should be at least 3 characters long and have only letters.";
        final String NO_LAST_NAME_PROVIDED_MESSAGE = "Should be provided a last name.";

        assertAll(
                () -> validFieldValidator.accept(CustomerDTO::setLastName, "Fonseca"),
                () -> invalidFieldValidator.accept(INVALID_LAST_NAME_MESSAGE, CustomerDTO::setLastName,"Gonç4lves"),
                () -> invalidFieldValidator.accept(INVALID_LAST_NAME_MESSAGE, CustomerDTO::setLastName,"Go"),
                () -> invalidFieldValidator.accept(NO_LAST_NAME_PROVIDED_MESSAGE, CustomerDTO::setLastName,"")
        );
    }

    @Test
    @DisplayName("CustomerDto Validation - validation of invalid birthdate.")
    void testValidationForBirthdate(){

        final String NO_BIRTHDATE_PROVIDED_MESSAGE = "A birthdate should be provided";
        final String INVALID_BIRTHDATE_MESSAGE = "Birthdate should be in valid format: 'dd-MM-yyyy' or 'dd/MM/yyyy'. Customer should be older than 14;";

        assertAll(
                () -> validFieldValidator.accept(CustomerDTO::setBirthdate,"20-10-1997"),
                () -> invalidFieldValidator.accept(INVALID_BIRTHDATE_MESSAGE, CustomerDTO::setBirthdate,"2002-10-12"),
                () -> invalidFieldValidator.accept(INVALID_BIRTHDATE_MESSAGE, CustomerDTO::setBirthdate,"31-20-2021"),
                () -> invalidFieldValidator.accept(INVALID_BIRTHDATE_MESSAGE, CustomerDTO::setBirthdate,"10-09-2018"),
                () -> invalidFieldValidator.accept(NO_BIRTHDATE_PROVIDED_MESSAGE, CustomerDTO::setBirthdate,"")
        );
    }

    @Test
    @DisplayName("CustomerDto Validation - validation of invalid gender.")
    void testValidationForGender(){
        final String INVALID_GENDER = "Should be provided a valid gender.";
        final String NO_GENDER_PROVIDED_MESSAGE = "Gender should be provided.";

        assertAll(
                () -> validFieldValidator.accept(CustomerDTO::setGender, "Other"),
                () -> validFieldValidator.accept(CustomerDTO::setGender, "Male"),
                () -> validFieldValidator.accept(CustomerDTO::setGender, "Female"),
                () -> validFieldValidator.accept(CustomerDTO::setGender, "Non-binary"),
                () -> invalidFieldValidator.accept(INVALID_GENDER, CustomerDTO::setGender,"males"),
                () -> invalidFieldValidator.accept(INVALID_GENDER, CustomerDTO::setGender,"woman"),
                () -> invalidFieldValidator.accept(NO_GENDER_PROVIDED_MESSAGE, CustomerDTO::setGender,"")
        );
    }

    @Test
    @DisplayName("CustomerDto Validation - validation of invalid email.")
    void testValidationForEmail(){
        final String INVALID_EMAIL = "Email should be provided in a correct format.";
        final String NO_EMAIL_PROVIDED_MESSAGE = "A valid email should be provided.";

        assertAll(
                () -> validFieldValidator.accept(CustomerDTO::setEmail, "email@fmail.com"),
                () -> invalidFieldValidator.accept(INVALID_EMAIL, CustomerDTO::setEmail,"asdfçljk"),
                () -> invalidFieldValidator.accept(NO_EMAIL_PROVIDED_MESSAGE, CustomerDTO::setEmail,""));
    }

    @Test
    @DisplayName("CustomerDto Validation - validation of invalid password.")
    void testValidationForPassword(){

        final String NO_PASSWORD_PROVIDED_MESSAGE = "Customer must have a password.";
        final String INVALID_PASSWORD_MESSAGE = "Password must be at least 6 characters long.";

        assertAll(
                () -> validFieldValidator.accept(CustomerDTO::setPassword, "umasenha"),
                () -> invalidFieldValidator.accept(INVALID_PASSWORD_MESSAGE, CustomerDTO::setPassword,"senha"),
                () -> invalidFieldValidator.accept(NO_PASSWORD_PROVIDED_MESSAGE, CustomerDTO::setPassword,"")
        );
    }

}
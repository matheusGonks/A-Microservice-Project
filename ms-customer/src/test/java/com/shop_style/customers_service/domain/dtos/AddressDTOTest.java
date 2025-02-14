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

class AddressDTOTest {

    private static Validator validator;
    private AddressDTO addressDTO;

    private final TriConsumer<String, BiConsumer<AddressDTO, String>, String> invalidFieldValidator = (expectedValidationMessage, fieldSetterMethod, setterArgument) -> {
        fieldSetterMethod.accept(addressDTO, setterArgument);
        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(addressDTO);
        assertTrue(violations.stream().anyMatch(
                        violation -> violation.getMessage().equals(expectedValidationMessage)),
                violations.toString()
        );
    };

    private final BiConsumer<BiConsumer<AddressDTO, String>, String> validFieldValidator = (fieldSetterMethod, fieldSetterArgument) -> {
        fieldSetterMethod.accept(addressDTO, fieldSetterArgument);
        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(addressDTO);
        assertTrue(violations.isEmpty(), violations.toString());
    };

    @BeforeAll
    public static void setUpValidator(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void setAddressDTO(){
        addressDTO = new AddressDTO();
        addressDTO.setId(1);
        addressDTO.setState("MG");
        addressDTO.setCity("Lavras");
        addressDTO.setDistrict("Meu distrito");
        addressDTO.setStreet("Rua Getúlio Vargas");
        addressDTO.setNumber("12");
        addressDTO.setZipcode("37890-900");
        addressDTO.setComplement("None");
        addressDTO.setCustomerId(1);
    }

    @Test
    @DisplayName("AddressDto Validation - validation of invalid state")
    public void testValidationForState(){

        final String INVALID_STATE_MESSAGE = "A valid state should be provided.";
        final String NO_STATE_PROVIDED_MESSAGE = "User should provide a state.";

        assertAll(
                () -> validFieldValidator.accept(AddressDTO::setState, "MG"),
                () -> validFieldValidator.accept(AddressDTO::setState, "SP"),
                () -> validFieldValidator.accept(AddressDTO::setState, "Rio de Janeiro"),
                () -> validFieldValidator.accept(AddressDTO::setState, "Maranhão"),
                () -> invalidFieldValidator.accept(INVALID_STATE_MESSAGE, AddressDTO::setState, "RE"),
                () -> invalidFieldValidator.accept(INVALID_STATE_MESSAGE, AddressDTO::setState, "ASD2"),
                () -> invalidFieldValidator.accept(NO_STATE_PROVIDED_MESSAGE, AddressDTO::setState, ""),
                () -> invalidFieldValidator.accept(NO_STATE_PROVIDED_MESSAGE, AddressDTO::setState, null )
        );

    }

    @Test
    @DisplayName("AddressDto Validation - validation of invalid city")
    public void testValidationForCity(){

        final String NO_CITY_PROVIDED_MESSAGE = "User should provide a valid city.";

        assertAll(
                () -> validFieldValidator.accept(AddressDTO::setCity, "Lavras"),
                () -> invalidFieldValidator.accept(NO_CITY_PROVIDED_MESSAGE, AddressDTO::setCity, ""),
                () -> invalidFieldValidator.accept(NO_CITY_PROVIDED_MESSAGE, AddressDTO::setCity, null)
        );
    }

    @Test
    @DisplayName("AddressDto Validation - validation of invalid district")
    public void testValidationForDistrict(){

        final String NO_DISTRICT_PROVIDED_MESSAGE = "User should provide a valid district.";

        assertAll(
                () -> validFieldValidator.accept(AddressDTO::setCity, "Some District"),
                () -> invalidFieldValidator.accept(NO_DISTRICT_PROVIDED_MESSAGE, AddressDTO::setDistrict, ""),
                () -> invalidFieldValidator.accept(NO_DISTRICT_PROVIDED_MESSAGE, AddressDTO::setDistrict, null)
        );
    }

    @Test
    @DisplayName("AddressDto Validation - validation of invalid street")
    public void testValidationForStreet(){

        final String NO_STREET_PROVIDED_MESSAGE = "User should provide a valid street.";

        assertAll(
                () -> validFieldValidator.accept(AddressDTO::setStreet, "Rua Professor Joseph"),
                () -> invalidFieldValidator.accept(NO_STREET_PROVIDED_MESSAGE, AddressDTO::setStreet, ""),
                () -> invalidFieldValidator.accept(NO_STREET_PROVIDED_MESSAGE, AddressDTO::setStreet, null)
        );
    }

    @Test
    @DisplayName("AddressDto Validation - validation of invalid number")
    public void testValidationForNumber(){

        final String NO_NUMBER_PROVIDED_MESSAGE = "User should provide a valid number.";

        assertAll(
                () -> validFieldValidator.accept(AddressDTO::setNumber, "A12A"),
                () -> invalidFieldValidator.accept(NO_NUMBER_PROVIDED_MESSAGE, AddressDTO::setNumber, ""),
                () -> invalidFieldValidator.accept(NO_NUMBER_PROVIDED_MESSAGE, AddressDTO::setNumber, null)
        );
    }

    @Test
    @DisplayName("AddressDto Validation - validation of invalid zipcode")
    public void testValidationForZipcode(){

        final String NO_ZIPCODE_PROVIDED_MESSAGE = "User should provide a valid zipcode.";
        final String INVALID_ZIPCODE_PROVIDED_MESSAGE = "User should provide a valid zipcode: xxxxx-xxx.";

        assertAll(
                () -> validFieldValidator.accept(AddressDTO::setZipcode, "37205-100"),
                () -> invalidFieldValidator.accept(INVALID_ZIPCODE_PROVIDED_MESSAGE, AddressDTO::setZipcode, "3720510012"),
                () -> invalidFieldValidator.accept(INVALID_ZIPCODE_PROVIDED_MESSAGE, AddressDTO::setZipcode, "37205-1001"),
                () -> invalidFieldValidator.accept(INVALID_ZIPCODE_PROVIDED_MESSAGE, AddressDTO::setZipcode, "3G2O5-1A1"),
                () -> invalidFieldValidator.accept(NO_ZIPCODE_PROVIDED_MESSAGE, AddressDTO::setZipcode, ""),
                () -> invalidFieldValidator.accept(NO_ZIPCODE_PROVIDED_MESSAGE, AddressDTO::setZipcode, null)
        );
    }

    @Test
    @DisplayName("AddressDto Validation - validation of Invalid Complement")
    public void testValidationForComplement(){

        assertAll(
                () -> validFieldValidator.accept(AddressDTO::setComplement, "Near the great red tree."),
                () -> validFieldValidator.accept(AddressDTO::setComplement, ""),
                () -> validFieldValidator.accept(AddressDTO::setComplement, null)
        );
    }
}
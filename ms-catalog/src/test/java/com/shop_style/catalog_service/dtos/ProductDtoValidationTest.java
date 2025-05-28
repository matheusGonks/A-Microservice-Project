package com.shop_style.catalog_service.dtos;

import com.shop_style.catalog_service.dtos.product.ProductDto;
import com.shop_style.catalog_service.stub_builders.ProductStubsBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ProductDtoValidationTest {

    private static Validator validator;

    ProductStubsBuilder productStubsBuilder;

    private boolean isExpectedMessagePresentInViolations(Set<ConstraintViolation<ProductDto>> violations, String message) {
        return violations
                .stream()
                .anyMatch((violation) -> violation.getMessage().equals(message));
    }

    @BeforeAll
    static void generalSetup() {
        ValidatorFactory validation = Validation.buildDefaultValidatorFactory();
        validator = validation.getValidator();
    }

    @BeforeEach
    void setup() {
        productStubsBuilder = new ProductStubsBuilder();
    }

    @Test
    @DisplayName("ProductDto Validation - validate product with blank name.")
    public void testValidationOfBlankName(){
        String EXPECTED_MESSAGE = "Product name is required.";
        ProductDto productForValidation = productStubsBuilder.withName("").getDto();

        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );

    }

    @Test
    @DisplayName("ProductDto Validation - verify product with null name.")
    public void testValidationOfNullName(){
        String EXPECTED_MESSAGE = "Product name is required.";
        ProductDto productForValidation = productStubsBuilder.withName(null).getDto();

        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("ProductDto Validation - validate product with blank brank.")
    public void testValidationOfBlankBrand(){
        String EXPECTED_MESSAGE = "Brand is required.";
        ProductDto productForValidation = productStubsBuilder.withBrand("").getDto();

        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );

    }

    @Test
    @DisplayName("ProductDto Validation - verify product with null active state.")
    public void testValidationOfNullActiveState(){
        String EXPECTED_MESSAGE = "Active state is required.";
        ProductDto productForValidation = productStubsBuilder.withActive(null).getDto();

        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("ProductDto Validation - verify product with null category Id.")
    public void testValidationOfNullCategoryId(){
        String EXPECTED_MESSAGE = "An associated category must be provided.";
        ProductDto productForValidation = productStubsBuilder.withCategoryId(null).getDto();

        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productForValidation);
        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

}
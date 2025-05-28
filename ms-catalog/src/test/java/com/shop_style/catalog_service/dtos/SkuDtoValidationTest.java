package com.shop_style.catalog_service.dtos;

import com.shop_style.catalog_service.dtos.sku.SkuDto;
import com.shop_style.catalog_service.stub_builders.SkuStubsBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SkuDtoValidationTest {

    private static Validator validator;

    SkuStubsBuilder skuStubsBuilder;

    private boolean isExpectedMessagePresentInViolations(Set<ConstraintViolation<SkuDto>> violations, String message) {
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
    public void setup(){
        skuStubsBuilder = new SkuStubsBuilder();
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with null price")
    public void validationOfSkuWithNullPrice(){
        String EXPECTED_MESSAGE = "Price is required.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withPrice(null).getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with value below minimum")
    public void validationOfSkuWithPriceUnderMinimum(){
        String EXPECTED_MESSAGE = "Price must be at least $0.01.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withPrice("-10.50").getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with value expressed with more than 2 decimal places")
    public void validationOfSkuWithPriceWithMoreThanTwoDecimals(){
        String EXPECTED_MESSAGE = "Price have two decimal places.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withPrice("0.001").getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with no quantity provided")
    public void validationOfSkuWithPriceWithNullQuantity(){
        String EXPECTED_MESSAGE = "A quantity must be provided.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withQuantity(null).getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with negative quantity")
    public void validationOfSkuWithPriceWithNegativeQuantity(){
        String EXPECTED_MESSAGE = "Quantity must be at least 0.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withQuantity(-10).getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with null size.")
    public void validationOfSkuWithPriceWithNullSize(){
        String EXPECTED_MESSAGE = "Size is required.";

        SkuDto skuDtoForValidation = skuStubsBuilder.withSize(null).getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with invalid size.")
    public void validationOfSkuWithPriceWithInvalidSize(){
        String EXPECTED_MESSAGE = "Size must be S, M, L, XL or XXL.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withSize("OL").getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with no color provided.")
    public void validationOfSkuWithPriceWithNoColor(){
        String EXPECTED_MESSAGE = "Color is required.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withColor(null).getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with no height provided.")
    public void validationOfSkuWithPriceWithNoHeight(){
        String EXPECTED_MESSAGE = "Height is required.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withHeight(null).getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with invalid height.")
    public void validationOfSkuWithPriceWithInvalidHeight(){
        String EXPECTED_MESSAGE = "Height must be provided in centimeters and must be at least 15.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withHeight(10).getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with no width provided.")
    public void validationOfSkuWithPriceWithNoWidth(){
        String EXPECTED_MESSAGE = "Width is required.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withWidth(null).getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with invalid width.")
    public void validationOfSkuWithPriceWithInvalidWidth(){
        String EXPECTED_MESSAGE = "Width must be provided in centimeters and must be at least 10.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withWidth(5).getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with null ProductId.")
    public void validationOfSkuWithPriceWithNoProductId(){
        String EXPECTED_MESSAGE = "Product id is required.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withProductId(null).getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("SkuDto Validation - validation of Sku with invalid ProductId.")
    public void validationOfSkuWithPriceWithNegativeProductId(){
        String EXPECTED_MESSAGE = "Product id must be at least 1.";
        SkuDto skuDtoForValidation = skuStubsBuilder.withProductId(-1L).getDto();

        Set<ConstraintViolation<SkuDto>> violations = validator.validate(skuDtoForValidation);

        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Expected violation was not found. Violations found :" + violations
        );
    }
}
package com.shop_style.catalog_service.dtos;

import com.shop_style.catalog_service.dtos.category.CategoryDTO;
import com.shop_style.catalog_service.stub_builders.CategoryStubsBuilder;
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
public class CategoryDtoValidationTest { // all these tests assume that the data type is coherent

    private static Validator validator;

    CategoryStubsBuilder categoryStubsBuilder;

    private boolean isExpectedMessagePresentInViolations(Set<ConstraintViolation<CategoryDTO>> violations, String message){
        return violations
                .stream()
                .anyMatch((violation) -> violation.getMessage().equals(message));
    }

    @BeforeAll
    static void generalSetup(){
        ValidatorFactory validation = Validation.buildDefaultValidatorFactory();
        validator = validation.getValidator();
    }


    @BeforeEach
    void setup(){
        categoryStubsBuilder = new CategoryStubsBuilder();
    }

    @Test
    @DisplayName("CategoryDto Validation - validation for valid category")
    public void categoryDtoValidationWithSuccess(){
        CategoryDTO dtoForValidation = categoryStubsBuilder.withName("Shirt").withActive(true).withParentId(2L).getDto();
        Set<ConstraintViolation<CategoryDTO>> violations = validator.validate(dtoForValidation);
        assertTrue(violations.isEmpty(), "Errors were caught when there shouldn't be any errors.");
    }

    @Test
    @DisplayName("CategoryDto Validation - validation for valid category")
    public void categoryDtoValidationWithSuccessForCategoryWithNoParent(){
        CategoryDTO dtoForValidation = categoryStubsBuilder.withName("Shirt").withActive(true).withParentId(null).getDto();
        Set<ConstraintViolation<CategoryDTO>> violations = validator.validate(dtoForValidation);
        assertTrue(violations.isEmpty(), "Errors were caught when there shouldn't be any errors.");
    }
    
    @Test
    @DisplayName("CategoryDTO Validation - validation for category with blank name")
    public void categoryDtoValidationUnsuccessfulForBlankName(){
        String EXPECTED_MESSAGE = "Category name can not be blank or null.";
        CategoryDTO dtoForValidation = categoryStubsBuilder.withName("").getDto();

        Set<ConstraintViolation<CategoryDTO>> violations = validator.validate(dtoForValidation);
        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Message for name violation wasn't found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("CategoryDto Validation - validation for category with null name ")
    public void categoryDtoValidationUnsuccessfulForNullName(){
        String EXPECTED_MESSAGE = "Category name can not be blank or null.";
        CategoryDTO dtoForValidation = categoryStubsBuilder.withName(null).getDto();

        Set<ConstraintViolation<CategoryDTO>> violations = validator.validate(dtoForValidation);
        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Message for name violation wasn't found. Violations found :" + violations
        );
    }

    @Test
    @DisplayName("CategoryDto Validation - validation for category with invalid parent Id.")
    public void categoryDtoValidationUnsuccessfulForInvalidParentId(){
        String EXPECTED_MESSAGE = "Category parent Id must be at least 1.";
        CategoryDTO dtoForValidation = categoryStubsBuilder.withParentId(0L).getDto();

        Set<ConstraintViolation<CategoryDTO>> violations = validator.validate(dtoForValidation);
        assertTrue(
                isExpectedMessagePresentInViolations(violations, EXPECTED_MESSAGE),
                "Message for name violation wasn't found. Violations found :" + violations
        );
    }

}

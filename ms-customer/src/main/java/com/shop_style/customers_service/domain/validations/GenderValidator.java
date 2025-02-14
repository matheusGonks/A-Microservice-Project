package com.shop_style.customers_service.domain.validations;

import com.shop_style.customers_service.domain.enums.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;

        return Arrays.stream(Gender.values())
                .anyMatch((gender) -> gender.name().replace("_", "-").equalsIgnoreCase(value));
    }
}

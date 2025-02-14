package com.shop_style.customers_service.domain.validations;

import com.shop_style.customers_service.domain.enums.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;


public class StateValidator implements ConstraintValidator<ValidState, String> {
    @Override
    public void initialize(ValidState constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;

        return Arrays.stream(State.values()).anyMatch((state) ->
            value.equalsIgnoreCase(state.name()) || value.equalsIgnoreCase(state.getFullName())
        );
    }
}

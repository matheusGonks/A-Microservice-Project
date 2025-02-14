package com.shop_style.customers_service.domain.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = BirthdateValidator.class)
@Documented
public @interface ValidBirthdate {

    String message() default "Birthdate should be in valid format: 'dd-MM-yyyy' or 'dd/MM/yyyy'. Customer should be older than 14;";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

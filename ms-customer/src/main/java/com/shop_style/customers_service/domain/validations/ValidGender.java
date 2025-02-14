package com.shop_style.customers_service.domain.validations;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
@Documented
public @interface ValidGender {

    String message() default "Should be provided a valid gender.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}

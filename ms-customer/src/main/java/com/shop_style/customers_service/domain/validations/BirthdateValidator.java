package com.shop_style.customers_service.domain.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiPredicate;

public class BirthdateValidator implements ConstraintValidator<ValidBirthdate, String> {

    private final DateTimeFormatter VALID_FORMAT_1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter VALID_FORMAT_2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private LocalDate parsedBirthdate;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isInValidFormat(value) && isOlderThan14(value);
    }

    private boolean isInValidFormat(String birthdate){
        BiPredicate<DateTimeFormatter, String> validateDateWithFormat = (format, providedBirthdate) ->  {
            try {
                parsedBirthdate = LocalDate.parse(providedBirthdate, format);
                return true;
            }catch (DateTimeException e){
                return false;
            }
        };

        return validateDateWithFormat.test(VALID_FORMAT_1, birthdate) || validateDateWithFormat.test(VALID_FORMAT_2, birthdate);

    }

    private boolean isOlderThan14(String birthdate){
        LocalDate today = LocalDate.now();
        LocalDate eighteenYearsAgo = today.minusYears(18);

        return !parsedBirthdate.isAfter(eighteenYearsAgo);
    }
}

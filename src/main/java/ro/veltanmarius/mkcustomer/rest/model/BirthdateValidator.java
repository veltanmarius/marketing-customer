package ro.veltanmarius.mkcustomer.rest.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class BirthdateValidator implements ConstraintValidator<ValidBirthdate, LocalDate> {
    @Override
    public boolean isValid(final LocalDate birthday, final ConstraintValidatorContext context) {
        return Period.between(birthday, LocalDate.now()).getYears() >=18;
    }
}
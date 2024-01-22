package ro.veltanmarius.mkcustomer.rest.model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author Marius Veltan
 */
@Documented
@Constraint(validatedBy = BirthdateValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBirthdate {
    String message() default "Invalid customer request. The birth date must be greater or equal than 18.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
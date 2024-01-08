package ro.veltanmarius.mkcustomer.rest.model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author Marius Veltan
 */
@Documented
@Constraint(validatedBy = CustomerValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCustomerRequest {
    String message() default "Invalid customer request. Set email or all address fields.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

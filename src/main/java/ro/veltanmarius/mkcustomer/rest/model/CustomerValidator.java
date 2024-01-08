package ro.veltanmarius.mkcustomer.rest.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.nonNull;

/**
 * @author Marius Veltan
 */
public class CustomerValidator implements ConstraintValidator<ValidCustomerRequest, CustomerDataRequest> {

    @Override
    public boolean isValid(CustomerDataRequest customerRequest, ConstraintValidatorContext context) {
        if (isValidString(customerRequest.getEmail())) {
            return true;
        }
        return isValidString(customerRequest.getStreet()) &&
                isValidString(customerRequest.getNumber()) &&
                isValidString(customerRequest.getZipCode()) &&
                isValidString(customerRequest.getCity()) &&
                isValidString(customerRequest.getCountry());
    }

    private boolean isValidString(String stringValue) {
        return nonNull(stringValue) && !stringValue.isBlank();
    }
}
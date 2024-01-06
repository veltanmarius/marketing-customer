package ro.veltanmarius.mkcustomer.model;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author Marius Veltan
 */
@AllArgsConstructor
@Getter
@SuperBuilder
public sealed class CustomerMutableData permits Customer {

    protected final int id;
    @Email
    protected final String email;
    protected final String street;
    protected final String number;
    protected final String zipCode;
    protected final String city;
    protected final String country;

    public boolean hasValidAddress() {
        if (email != null && email.trim().length() > 0) {
            return true;
        }
        if (street != null && street.trim().length() > 0 &&
                number != null && number.trim().length() > 0 &&
                zipCode != null && zipCode.trim().length() > 0 &&
                city != null && city.trim().length() > 0 &&
                country != null && country.trim().length() > 0) {
            return true;
        }
        return false;
    }
}
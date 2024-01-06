package ro.veltanmarius.mkcustomer.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

/**
 * @author Marius Veltan
 */
@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public final class Customer extends CustomerMutableData {

    @NotBlank
    private final String firstName;
    @NotBlank
    private final String lastName;
    @Min(value=18)
    private final int age;

    public Customer(int id, String firstName, String lastName, int age, String email, String street, String number, String zipCode, String city, String country) {
        super(id, email, street, number, zipCode, city, country);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

}
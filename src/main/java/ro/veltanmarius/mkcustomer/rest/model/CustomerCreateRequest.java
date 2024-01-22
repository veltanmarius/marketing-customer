package ro.veltanmarius.mkcustomer.rest.model;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.time.LocalDate;

/**
 * @author Marius Veltan
 */
@Value
@ValidCustomerRequest
public class CustomerCreateRequest implements CustomerDataRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;
    @NotNull
    @Past
    @ValidBirthdate
    private LocalDate birthday;
    @Email
    private String email;
    private String street;
    private String number;
    private String zipCode;
    private String city;
    private String country;

}
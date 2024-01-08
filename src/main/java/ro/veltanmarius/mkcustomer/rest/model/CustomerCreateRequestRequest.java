package ro.veltanmarius.mkcustomer.rest.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

/**
 * @author Marius Veltan
 */
@Value
@ValidCustomerRequest
public class CustomerCreateRequestRequest implements CustomerDataRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Min(value=18)
    private int age;
    @Email
    private String email;
    private String street;
    private String number;
    private String zipCode;
    private String city;
    private String country;

}
package ro.veltanmarius.mkcustomer.rest.model;

import jakarta.validation.constraints.Email;
import lombok.Value;

/**
 * @author Marius Veltan
 */
@Value
@ValidCustomerRequest
public class CustomerUpdateRequestRequest implements CustomerDataRequest {

    private int id;
    @Email
    private String email;
    private String street;
    private String number;
    private String zipCode;
    private String city;
    private String country;

}
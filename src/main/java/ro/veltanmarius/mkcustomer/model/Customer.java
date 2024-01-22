package ro.veltanmarius.mkcustomer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.Period;

/**
 * @author Marius Veltan
 */
@Value
@Builder
@AllArgsConstructor
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String street;
    private String number;
    private String zipCode;
    private String city;
    private String country;

    public Integer getAge() {
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}
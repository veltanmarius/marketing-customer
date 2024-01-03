package ro.veltanmarius.mkcustomer.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Customer extends CustomerMutableData {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Min(value=18)
    private int age;

    public Customer() {
        this.id = 0;
        this.firstName = null;
        this.lastName = null;
        this.age = 0;
        this.email = null;
        this.street = null;
        this.number = null;
        this.zipCode = null;
        this.city = null;
        this.country = null;
    }

    public Customer(int id, String firstName, String lastName, int age, String email, String street, String number, String zipCode, String city, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
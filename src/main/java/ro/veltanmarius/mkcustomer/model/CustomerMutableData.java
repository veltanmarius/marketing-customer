package ro.veltanmarius.mkcustomer.model;

import jakarta.validation.constraints.Email;

/**
 * @author Marius Veltan
 */
public sealed class CustomerMutableData permits Customer {

    protected int id;
    @Email
    protected String email;
    protected String street;
    protected String number;
    protected String zipCode;
    protected String city;
    protected String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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
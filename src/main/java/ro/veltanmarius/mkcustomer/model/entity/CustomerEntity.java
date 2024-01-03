package ro.veltanmarius.mkcustomer.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "customer")
public class CustomerEntity extends AbstractEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Version
    private Integer version;

    @Column(name="first_name")
    @NotNull
    private String firstName;
    @Column(name="last_name")
    @NotNull
    private String lastName;
    private Integer age;
    private String email;
    private String street;
    private String number;
    @Column(name="zip_code")
    private String zipCode;
    private String city;
    private String country;

    public CustomerEntity() {
        version = 0;
    }

    public CustomerEntity(Integer id, String firstName, String lastName, Integer age, String email, String street, String number, String zipCode, String city, String country) {
        version = 0;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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
}
package ro.veltanmarius.mkcustomer.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Marius Veltan
 */
@Entity
@Table(name = "customer",
        indexes = {
            @Index(name = "index_fn", columnList = "first_name"),
            @Index(name = "index_ln", columnList = "last_name")
        })
@Data
@EqualsAndHashCode(callSuper = true)
public final class CustomerEntity extends AbstractEntity {
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

}
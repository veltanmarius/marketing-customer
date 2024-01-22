package ro.veltanmarius.mkcustomer.model.mapper;

import org.springframework.stereotype.Component;
import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.model.entity.CustomerEntity;
import ro.veltanmarius.mkcustomer.rest.model.CustomerCreateRequest;

import java.time.LocalDate;
import java.time.Period;

/**
 * @author Marius Veltan
 */
@Component
public final class CustomerMapper {

    public Customer entityToApi(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthday(entity.getBirthday())
                .email(entity.getEmail())
                .street(entity.getStreet())
                .number(entity.getNumber())
                .zipCode(entity.getZipCode())
                .city(entity.getCity())
                .country(entity.getCountry())
                .build();
    }

    public CustomerEntity apiToEntity(Customer api) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(api.getId());
        customerEntity.setFirstName(api.getFirstName());
        customerEntity.setLastName(api.getLastName());
        customerEntity.setBirthday(api.getBirthday());
        customerEntity.setEmail(api.getEmail());
        customerEntity.setStreet(api.getStreet());
        customerEntity.setNumber(api.getNumber());
        customerEntity.setZipCode(api.getZipCode());
        customerEntity.setCity(api.getCity());
        customerEntity.setCountry(api.getCountry());
        return customerEntity;
    }

    public CustomerEntity apiToEntity(CustomerCreateRequest api) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName(api.getFirstName());
        customerEntity.setLastName(api.getLastName());
        customerEntity.setBirthday(api.getBirthday());
        customerEntity.setEmail(api.getEmail());
        customerEntity.setStreet(api.getStreet());
        customerEntity.setNumber(api.getNumber());
        customerEntity.setZipCode(api.getZipCode());
        customerEntity.setCity(api.getCity());
        customerEntity.setCountry(api.getCountry());
        return customerEntity;
    }
}
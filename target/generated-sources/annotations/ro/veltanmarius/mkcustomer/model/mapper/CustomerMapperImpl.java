package ro.veltanmarius.mkcustomer.model.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.model.entity.CustomerEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-03T23:52:16+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer entityToApi(CustomerEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Customer customer = new Customer();

        if ( entity.getId() != null ) {
            customer.setId( entity.getId() );
        }
        customer.setEmail( entity.getEmail() );
        customer.setStreet( entity.getStreet() );
        customer.setNumber( entity.getNumber() );
        customer.setZipCode( entity.getZipCode() );
        customer.setCity( entity.getCity() );
        customer.setCountry( entity.getCountry() );
        customer.setFirstName( entity.getFirstName() );
        customer.setLastName( entity.getLastName() );
        if ( entity.getAge() != null ) {
            customer.setAge( entity.getAge() );
        }

        return customer;
    }

    @Override
    public CustomerEntity apiToEntity(Customer api) {
        if ( api == null ) {
            return null;
        }

        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setId( api.getId() );
        customerEntity.setFirstName( api.getFirstName() );
        customerEntity.setLastName( api.getLastName() );
        customerEntity.setAge( api.getAge() );
        customerEntity.setEmail( api.getEmail() );
        customerEntity.setStreet( api.getStreet() );
        customerEntity.setNumber( api.getNumber() );
        customerEntity.setZipCode( api.getZipCode() );
        customerEntity.setCity( api.getCity() );
        customerEntity.setCountry( api.getCountry() );

        return customerEntity;
    }
}

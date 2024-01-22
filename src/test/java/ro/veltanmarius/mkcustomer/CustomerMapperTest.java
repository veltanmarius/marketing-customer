package ro.veltanmarius.mkcustomer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.model.entity.CustomerEntity;
import ro.veltanmarius.mkcustomer.model.mapper.CustomerMapper;
import ro.veltanmarius.mkcustomer.rest.model.CustomerCreateRequest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerMapperTest {

    private final CustomerMapper customerMapper = new CustomerMapper();
    private final LocalDate dateOfBirth = LocalDate.of(1999, 9, 9);
    @Test
    void mapperCustomerTests() {
        Customer customer = new Customer(1, "fn", "ln", dateOfBirth, "e@x.ro", "st", "no", "zp", "ci", "co");
        CustomerEntity customerEntity = new CustomerEntity(1, "fn", "ln", dateOfBirth, "e@x.ro", "st", "no", "zp", "ci", "co");
        Assertions.assertThat(customerMapper.apiToEntity(customer)).isEqualTo(customerEntity);

        Customer c = customerMapper.entityToApi(customerEntity);
        assertEquals(c.getId(), customer.getId());
        assertEquals(c.getFirstName(), customer.getFirstName());
        assertEquals(c.getLastName(), customer.getLastName());
        assertEquals(c.getBirthday(), customer.getBirthday());
        assertEquals(c.getEmail(), customer.getEmail());
        assertEquals(c.getStreet(), customer.getStreet());
        assertEquals(c.getNumber(), customer.getNumber());
        assertEquals(c.getZipCode(), customer.getZipCode());
        assertEquals(c.getCity(), customer.getCity());
        assertEquals(c.getCountry(), customer.getCountry());
    }

    @Test
    void mapperCustomerCreateRequestTests() {
        CustomerCreateRequest customer = new CustomerCreateRequest("fn", "ln", dateOfBirth, "e@x.ro", "st", "no", "zp", "ci", "co");
        CustomerEntity initialCustomerEntity = customerMapper.apiToEntity(customer);
        initialCustomerEntity.setId(1);
        CustomerEntity expectedCustomerEntity = new CustomerEntity(1, "fn", "ln", dateOfBirth, "e@x.ro", "st", "no", "zp", "ci", "co");
        Assertions.assertThat(initialCustomerEntity).isEqualTo(expectedCustomerEntity);
    }
}
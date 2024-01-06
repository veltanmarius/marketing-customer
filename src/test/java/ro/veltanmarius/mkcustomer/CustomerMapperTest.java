package ro.veltanmarius.mkcustomer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.model.entity.CustomerEntity;
import ro.veltanmarius.mkcustomer.model.mapper.CustomerMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerMapperTest {

    CustomerMapper customerMapper = new CustomerMapper();
    @Test
    void mapperTests() {
        Customer customer = new Customer(1, "fn", "ln", 18, "e@x.ro", "st", "no", "zp", "ci", "co");
        CustomerEntity customerEntity = new CustomerEntity(1, "fn", "ln", 18, "e@x.ro", "st", "no", "zp", "ci", "co");
        Assertions.assertThat(customerMapper.apiToEntity(customer)).isEqualTo(customerEntity);

        Customer c = customerMapper.entityToApi(customerEntity);
        assertEquals(c.getId(), customer.getId());
        assertEquals(c.getFirstName(), customer.getFirstName());
        assertEquals(c.getLastName(), customer.getLastName());
        assertEquals(c.getAge(), customer.getAge());
        assertEquals(c.getEmail(), customer.getEmail());
        assertEquals(c.getStreet(), customer.getStreet());
        assertEquals(c.getNumber(), customer.getNumber());
        assertEquals(c.getZipCode(), customer.getZipCode());
        assertEquals(c.getCity(), customer.getCity());
        assertEquals(c.getCountry(), customer.getCountry());
    }
}
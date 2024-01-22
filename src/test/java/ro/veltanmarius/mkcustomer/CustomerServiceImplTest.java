package ro.veltanmarius.mkcustomer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.model.entity.CustomerEntity;
import ro.veltanmarius.mkcustomer.model.mapper.CustomerMapper;
import ro.veltanmarius.mkcustomer.repository.CustomerRepository;
import ro.veltanmarius.mkcustomer.rest.model.CustomerCreateRequest;
import ro.veltanmarius.mkcustomer.service.CustomerServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Marius Veltan
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    private final LocalDate dateOfBirth = LocalDate.of(1999, 9, 9);

    private CustomerEntity getExpectedCustomerEntity() {
        return new CustomerEntity(1, "firstName", "lastName", dateOfBirth, "email@email.com", "st", "no", "zp", "ci", "co");
    }

    private CustomerEntity getInitialCustomerEntity() {
        return new CustomerEntity(null, "firstName", "lastName", dateOfBirth, "email@email.com", "st", "no", "zp", "ci", "co");
    }

    private CustomerCreateRequest getCustomerCreateRequest() {
        return new CustomerCreateRequest("firstName", "lastName", dateOfBirth, "email@email.com", "st", "no", "zp", "ci", "co");
    }

    private Customer getExpectedCustomer() {
        return new Customer(1, "firstName", "lastName", dateOfBirth, "email@email.com", "st", "no", "zp", "ci", "co");
    }

    @Test
    void createCustomerSuccess() {
        CustomerEntity initialCustomerEntity = getInitialCustomerEntity();
        CustomerCreateRequest customerCreateRequest = getCustomerCreateRequest();

        when(customerRepository.save(initialCustomerEntity)).thenReturn(getExpectedCustomerEntity());
        when(customerMapper.apiToEntity(customerCreateRequest)).thenReturn(initialCustomerEntity);
        when(customerMapper.entityToApi(getExpectedCustomerEntity())).thenReturn(getExpectedCustomer());

        assertDoesNotThrow(() ->customerService.createCustomer(customerCreateRequest));
        assertEquals(getExpectedCustomer(), customerService.createCustomer(customerCreateRequest));
    }
}
package ro.veltanmarius.mkcustomer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.veltanmarius.mkcustomer.exceptions.ObjectNotFoundException;
import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.rest.model.CustomerCreateRequest;
import ro.veltanmarius.mkcustomer.rest.model.CustomerUpdateRequest;
import ro.veltanmarius.mkcustomer.model.entity.CustomerEntity;
import ro.veltanmarius.mkcustomer.model.mapper.CustomerMapper;
import ro.veltanmarius.mkcustomer.repository.CustomerRepository;

import java.util.List;

/**
 * @author Marius Veltan
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public Customer createCustomer(CustomerCreateRequest createRequest) {
        CustomerEntity customerEntity = customerRepository.save(customerMapper.apiToEntity(createRequest));
        log.debug("createCustomer: customer entity with ID {} was created for {} {}", customerEntity.getId(), createRequest.getFirstName(), createRequest.getLastName());
        return customerMapper.entityToApi(customerEntity);
    }

    @Override
    @Transactional
    public Customer updateCustomerEmailAndAddress(CustomerUpdateRequest customerRequest) {
        CustomerEntity customerEntity = customerRepository.findById(customerRequest.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Customer does not exist for ID " + customerRequest.getId()));

        customerEntity.setEmail(customerRequest.getEmail());
        customerEntity.setStreet(customerRequest.getStreet());
        customerEntity.setNumber(customerRequest.getNumber());
        customerEntity.setZipCode(customerRequest.getZipCode());
        customerEntity.setCity(customerRequest.getCity());
        customerEntity.setCountry(customerRequest.getCountry());

        CustomerEntity newEntity = customerRepository.save(customerEntity);
        log.debug("updateCustomer: customer entity with ID {} was updated for {} {}", customerEntity.getId(), newEntity.getFirstName(), newEntity.getLastName());
        return customerMapper.entityToApi(newEntity);
    }

    @Override
    public Customer getCustomer(int customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException("Customer does not exist for ID " + customerId));
        Customer customer = customerMapper.entityToApi(customerEntity);
        log.debug("getCustomer: customer entity found {} {} {}", customer.getId(), customer.getFirstName(), customer.getLastName());
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::entityToApi).toList();
    }

    @Override
    public List<Customer> findCustomersExactMatch(String firstName, String lastName) {
        log.debug("getCustomersByName: {} or {}", firstName, lastName);
        return customerRepository.findCustomerByFirstNameOrLastName(firstName, lastName).stream().map(customerMapper::entityToApi).toList();
    }

    @Override
    public List<Customer> searchCustomersPartialMatch(String firstName, String lastName) {
        log.debug("searchCustomersByName: {} or {}", firstName, lastName);
        if (StringUtils.isAllEmpty(firstName, lastName)) {
            log.debug("searchCustomersByName: No values for parameters firstName and lastName, all customers will be returned");
            return getAllCustomers();
        }
        return customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName).stream().map(customerMapper::entityToApi).toList();
    }
}
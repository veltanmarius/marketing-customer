package ro.veltanmarius.mkcustomer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.veltanmarius.mkcustomer.exceptions.ObjectNotFoundException;
import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.model.CustomerMutableData;
import ro.veltanmarius.mkcustomer.model.entity.CustomerEntity;
import ro.veltanmarius.mkcustomer.model.mapper.CustomerMapper;
import ro.veltanmarius.mkcustomer.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerMapper customerMapper;

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public Customer createCustomer(Customer customer) {
        CustomerEntity customerEntity = customerRepository.save(customerMapper.apiToEntity(customer));
        LOG.debug("createCustomer: customer entity with ID {} was created for {} {}", customerEntity.getId(), customer.getFirstName(), customer.getLastName());
        return customerMapper.entityToApi(customerEntity);
    }

    @Transactional
    @Override
    public Customer updateCustomerEmailAndAddress(CustomerMutableData customerMutableData) {
        CustomerEntity customerEntity = customerRepository.findById(customerMutableData.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Customer does not exist for ID " + customerMutableData.getId()));

        customerEntity.setEmail(customerMutableData.getEmail());
        customerEntity.setStreet(customerMutableData.getStreet());
        customerEntity.setNumber(customerMutableData.getNumber());
        customerEntity.setZipCode(customerMutableData.getZipCode());
        customerEntity.setCity(customerMutableData.getCity());
        customerEntity.setCountry(customerMutableData.getCountry());

        CustomerEntity newEntity = customerRepository.save(customerEntity);
        LOG.debug("updateCustomer: customer entity with ID {} was updated for {} {}", customerEntity.getId(), newEntity.getFirstName(), newEntity.getLastName());
        return customerMapper.entityToApi(newEntity);
    }

    @Override
    public Customer getCustomer(int customerId) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(customerId);
        Customer customer = customerEntity.isPresent() ? customerMapper.entityToApi(customerEntity.get()) : new Customer();
        LOG.debug("getCustomer: customer entity found {} {} {}", customer.getId(), customer.getFirstName(), customer.getLastName());
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::entityToApi).toList();
    }

    @Override
    public List<Customer> getAllCustomers(String firstName, String lastName) {
        return customerRepository.findCustomerByFirstNameOrLastName(firstName, lastName).stream().map(customerMapper::entityToApi).toList();
    }

}
package ro.veltanmarius.mkcustomer.service;

import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.model.CustomerMutableData;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    Customer updateCustomerEmailAndAddress(CustomerMutableData customer);

    Customer getCustomer(int customerId);

    List<Customer> getAllCustomers();

    List<Customer> getAllCustomers(String firstName, String lastName);
}
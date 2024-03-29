package ro.veltanmarius.mkcustomer.service;

import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.rest.model.CustomerCreateRequest;
import ro.veltanmarius.mkcustomer.rest.model.CustomerUpdateRequest;

import java.util.List;

/**
 * @author Marius Veltan
 */
public interface CustomerService {
    /**
     * Create customer
     * @param customer request
     * @return the created customer
     */
    Customer createCustomer(CustomerCreateRequest customer);

    /**
     * Update customer
     * @param customer request
     * @return  the updated customer
     */
    Customer updateCustomerEmailAndAddress(CustomerUpdateRequest customer);

    /**
     * Get customer by ID
     * @param customerId the customer ID
     * @return the customer
     */
    Customer getCustomer(int customerId);

    /**
     * Get the customers
     * @return all the customers from the database
     */
    List<Customer> getAllCustomers();

    /**
     * Get the customers with exact first name or exact last name
     * @param firstName the first name
     * @param lastName the last name
     * @return the matching customers from the database
     */
    List<Customer> findCustomersExactMatch(String firstName, String lastName);

    /**
     * Get the customers containing first name or last name
     * @param firstName the first name
     * @param lastName the last name
     * @return the matching customers from the database
     */
    List<Customer> searchCustomersPartialMatch(String firstName, String lastName);
}
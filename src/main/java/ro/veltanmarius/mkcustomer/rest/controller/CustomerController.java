package ro.veltanmarius.mkcustomer.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.veltanmarius.mkcustomer.exceptions.InvalidInputException;
import ro.veltanmarius.mkcustomer.model.Customer;
import org.springframework.web.bind.annotation.*;
import ro.veltanmarius.mkcustomer.rest.model.CustomerCreateRequest;
import ro.veltanmarius.mkcustomer.rest.model.CustomerUpdateRequest;
import ro.veltanmarius.mkcustomer.service.CustomerService;

import java.util.List;

/**
 * @author Marius Veltan
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name = "CustomerController", description = "REST API for customer information.")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Sample usage, see below.
     * curl -X POST $HOST:$PORT/marketing/customers \
     * -H "Content-Type: application/json" --data \
     * '{"firstName":"my firstName","lastName": "my lastName", "age":18, "email":"contact@email.com"}'
     *
     * @param customerCreateRequest A JSON representation of the new customer
     */
    @Operation(
            summary = "${api.customer-info.create-customer.description}",
            description = "${api.customer-info.create-customer.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${api.responseCodes.created.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerCreateRequest customerCreateRequest) {
        log.debug("createCustomer: creates a new customer entity for {} {}", customerCreateRequest.getFirstName(), customerCreateRequest.getLastName());
        Customer customer = customerService.createCustomer(customerCreateRequest);
        log.debug("createCustomer: customer entity was created, {}", customerCreateRequest);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    /**
     * Sample usage, see below.
     * curl -X PUT $HOST:$PORT/marketing/customers \
     * -H "Content-Type: application/json" --data \
     * '{"id":123, "email":"contact@email.com"}'
     *
     * @param customerUpdateRequest A JSON representation of the updated customer
     */
    @Operation(
            summary = "${api.customer-info.update-customer.description}",
            description = "${api.customer-info.update-customer.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @PutMapping("/customer")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        log.debug("updateCustomer: update the customer entity with ID {}", customerUpdateRequest.getId());
        Customer customer = customerService.updateCustomerEmailAndAddress(customerUpdateRequest);
        log.debug("updateCustomer: customer entity was updated, ID: {}", customerUpdateRequest.getId());
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /**
     * Sample usage: "curl $HOST:$PORT/marketing/customers/1".
     *
     * @param customerId ID of the customer
     * @return the customer with the provided ID
     */
    @Operation(
            summary = "${api.customer-info.get-customer.description}",
            description = "${api.customer-info.get-customer.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Integer customerId) {
        log.debug("Will call getCustomerEntity for customer with ID: {}", customerId);
        if (customerId < 1) {
            throw new InvalidInputException("Invalid customerId: " + customerId);
        }

        log.debug("getCustomer: lookup a customer for customerId: {}", customerId);
        return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
    }

    /**
     * Retrieve customers as JSON representation
     * @return the customers
     */
    @Operation(
            summary = "${api.customer-info.get-customers-all.description}",
            description = "${api.customer-info.get-customers-all.notes}")
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        log.debug("getCustomers: Retrieve all customers");
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    /**
     * Retrieve customers as JSON representation
     * @param firstName the first name
     * @param lastName the last name
     * @return the customers
     */
    @Operation(
            summary = "${api.customer-info.get-customers-find.description}",
            description = "${api.customer-info.get-customers-find.notes}")
    @GetMapping("/customers/findExact")
    public ResponseEntity<List<Customer>> findCustomersExactMatch(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName) {
        log.debug("getCustomersByName: Find customers by first name or last name: {}, {}", firstName, lastName);
        return new ResponseEntity<>(customerService.findCustomersExactMatch(firstName, lastName), HttpStatus.OK);
    }

    /**
     * Retrieve customers as JSON representation
     * @param firstName the first name
     * @param lastName the last name
     * @return the customers
     */
    @Operation(
            summary = "${api.customer-info.get-customers-search.description}",
            description = "${api.customer-info.get-customers-search.notes}")
    @GetMapping("/customers/search")
    public ResponseEntity<List<Customer>> searchCustomersPartialMatch(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName) {
        log.debug("searchCustomersByName: search customers by first name or last name: {}, {}", firstName, lastName);
        return new ResponseEntity<>(customerService.searchCustomersPartialMatch(firstName, lastName), HttpStatus.OK);
    }
}
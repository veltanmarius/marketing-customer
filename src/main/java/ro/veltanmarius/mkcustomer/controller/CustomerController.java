package ro.veltanmarius.mkcustomer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ro.veltanmarius.mkcustomer.exceptions.InvalidInputException;
import ro.veltanmarius.mkcustomer.exceptions.ObjectNotFoundException;
import ro.veltanmarius.mkcustomer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ro.veltanmarius.mkcustomer.model.CustomerMutableData;
import ro.veltanmarius.mkcustomer.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/marketing/customers")
@Tag(name = "CustomerController", description = "REST API for customer information.")
public class CustomerController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Sample usage, see below.
     * curl -X POST $HOST:$PORT/marketing/customers \
     *   -H "Content-Type: application/json" --data \
     *   '{"customerId":123,"firstName":"my firstName","lastName": "my lastName", "email":"contact@email.com"}'
     *
     * @param body A JSON representation of the new customer
     */
    @Operation(
            summary = "${api.customer-info.create-customer.description}",
            description = "${api.customer-info.create-customer.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @PostMapping()
    void createCustomer(@Valid @RequestBody Customer body) {
        LOG.debug("createCustomer: creates a new customer entity for {} {}", body.getFirstName(), body.getLastName());

        if (!body.hasValidAddress()) {
            throw new InvalidInputException("Invalid customer!");
        }
        customerService.createCustomer(body);
        LOG.debug("createCustomer: customer entity was created, ID: {}", body.getId());
    }

    /**
     * Sample usage, see below.
     * curl -X PUT $HOST:$PORT/marketing/customers \
     *   -H "Content-Type: application/json" --data \
     *   '{"customerId":123, "email":"contact@email.com"}'
     *
     * @param body A JSON representation of the updated customer
     */
    @Operation(
            summary = "${api.customer-info.update-customer.description}",
            description = "${api.customer-info.update-customer.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @PutMapping()
    public void updateCustomer(@Valid @RequestBody CustomerMutableData body) {
        LOG.debug("updateCustomer: update the customer entity with ID {}", body.getId());

        if (!body.hasValidAddress()) {
            throw new InvalidInputException("Invalid customer!");
        }
        customerService.updateCustomerEmailAndAddress(body);
        LOG.debug("updateCustomer: customer entity was updated, ID: {}", body.getId());
    }

    /**
     * Sample usage: "curl $HOST:$PORT/marketing/customer/1".
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
    @GetMapping("/{customerId}")
    Customer getCustomer(@PathVariable Integer customerId) {
        LOG.debug("Will call getCustomerEntity for customer with ID: {}", customerId);
        if (customerId < 1) {
            throw new InvalidInputException("Invalid customerId: " + customerId);
        }

        LOG.debug("getCustomer: lookup a customer for customerId: {}", customerId);
        Customer customer = customerService.getCustomer(customerId);
        if (customer.getId() == 0) {
            throw new ObjectNotFoundException("No customer found for customerId: " + customerId);
        }

        return customer;
    }

    @Operation(
            summary = "${api.customer-info.get-customers-all.description}",
            description = "${api.customer-info.get-customers-all.notes}")
    @GetMapping()
    public List<Customer> getCustomers() {
        LOG.debug("getCustomers: Retrieve all customers");
        return customerService.getAllCustomers();
    }

    @Operation(
            summary = "${api.customer-info.get-customers-find.description}",
            description = "${api.customer-info.get-customers-find.notes}")
    @GetMapping("/find")
    public List<Customer> getCustomersByName(@RequestParam(value = "firstName", required = false) String firstName,
                                      @RequestParam(value = "lastName", required = false) String lastName) {
        LOG.debug("getCustomersByName: Find customers by first name or last name: {}, {}", firstName,  lastName);
        return customerService.getAllCustomers(firstName, lastName);
    }

    @Operation(
            summary = "${api.customer-info.get-customers-search.description}",
            description = "${api.customer-info.get-customers-search.notes}")
    @GetMapping("/search")
    public List<Customer> searchCustomersByName(@RequestParam(value = "firstName", required = false) String firstName,
                                             @RequestParam(value = "lastName", required = false) String lastName) {
        LOG.debug("searchCustomersByName: Search customers by first name or last name: {}, {}", firstName,  lastName);
        if (StringUtils.isAllEmpty(firstName, lastName)) {
            return customerService.getAllCustomers();
        }
        return customerService.searchAllCustomers(firstName, lastName);
    }
}
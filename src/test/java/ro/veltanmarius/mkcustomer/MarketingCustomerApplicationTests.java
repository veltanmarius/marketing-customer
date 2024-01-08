package ro.veltanmarius.mkcustomer;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import ro.veltanmarius.mkcustomer.exceptions.InvalidInputException;
import ro.veltanmarius.mkcustomer.exceptions.ObjectNotFoundException;
import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.service.CustomerService;

import java.util.List;

/**
 * @author Marius Veltan
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
class MarketingCustomerApplicationTests {

	private static final String CUSTOMER_URL="/marketing/customers";
	private static final int CUSTOMER_ID_OK = 1;
	private static final int CUSTOMER_ID_NOT_FOUND = 20;
	private static final int CUSTOMER_ID_INVALID = -1;

	@Autowired private WebTestClient client;

	@MockBean private CustomerService customerService;

	private Customer customer;

	@BeforeEach
	void setUp() {
		customer = new Customer(CUSTOMER_ID_OK, "fn", "ln", 18, "e@x.ro", "st", "no", "zp", "ci", "co");
		when(customerService.getCustomer(CUSTOMER_ID_OK))
				.thenReturn(customer);

		when(customerService.getCustomer(CUSTOMER_ID_NOT_FOUND))
				.thenThrow(new ObjectNotFoundException("NOT FOUND: " + CUSTOMER_ID_NOT_FOUND));
		when(customerService.getCustomer(CUSTOMER_ID_INVALID))
				.thenThrow(new InvalidInputException("Invalid customerId: " + CUSTOMER_ID_INVALID));

		when(customerService.getAllCustomers())
				.thenReturn(List.of(
						customer,
						new Customer(2, "fn2", "ln2", 18, "e2@x.ro", "st", "no", "zp", "ci", "co")
				));
		when(customerService.searchCustomersPartialMatch("fn", "ln"))
				.thenReturn(List.of(
						customer
				));
	}

	@Test
	void contextLoads() {
		log.debug("contextLoads: Tests are configured correctly");
	}

	@Test
	void createCustomerOK() {
		Customer customer;
		customer = new Customer(1, "fn", "ln", 18, "e@x.ro", "st", "no", "zp", "ci", "co");
		postAndVerifyCustomer(customer, OK);
		customer = new Customer(1, "fn ", "ln ", 18, "", "st", "no", "zp", "ci", "co");
		postAndVerifyCustomer(customer, OK);
		customer = new Customer(1, " FN", " LN", 18, "e@x.ro", " ", "", "", "", "");
		postAndVerifyCustomer(customer, OK);
	}

	@Test
	void createCustomerMandatoryFieldsMissing() {
		Customer customer;
		customer = new Customer(2, " ", " ", 18, "", "", "", "", "", "");
		postAndVerifyCustomer(customer, BAD_REQUEST);
		customer = new Customer(2, "fn", " ", 18, "", "", "", "", "", "");
		postAndVerifyCustomer(customer, BAD_REQUEST);
		customer = new Customer(2, "fn", "ln", 18, "", "", "", "", "", "");
		postAndVerifyCustomer(customer, BAD_REQUEST);
		customer = new Customer(2, "fn", "ln", 18, "", "st", "", "", "", "");
		postAndVerifyCustomer(customer, BAD_REQUEST);
		customer = new Customer(2, "fn", "ln", 18, "", "st", "no", "", "", "");
		postAndVerifyCustomer(customer, BAD_REQUEST);
		customer = new Customer(2, "fn", "ln", 18, "", "st", "no", "zp", "", "");
		postAndVerifyCustomer(customer, BAD_REQUEST);
		customer = new Customer(2, "fn", "ln", 18, "", "st", "no", "zp", "ci", "");
		postAndVerifyCustomer(customer, BAD_REQUEST);
	}

	@Test
	void createCustomerConstraintsFailed() {
		Customer customer;
		customer = new Customer(3, "fn", "ln", 18, "e@x.ro", "st", "no", "zp", "ci", "co");
		postAndVerifyCustomer(customer, OK);
		customer = new Customer(3, "fn", "ln", 18, "wrong", "st", "no", "zp", "ci", "co");
		postAndVerifyCustomer(customer, BAD_REQUEST);
		customer = new Customer(3, "fn", "ln", 17, "e@x.ro", "st", "no", "zp", "ci", "co");
		postAndVerifyCustomer(customer, BAD_REQUEST);
		customer = new Customer(3, "fn", "ln", -1, "e@x.ro", "st", "no", "zp", "ci", "co");
		postAndVerifyCustomer(customer, BAD_REQUEST);
	}

	@Test
	void updateCustomerOK() {
		Customer customer;
		customer = new Customer(1, null, null, 18, "e@x.ro", "st", "no", "zp", "ci", "co");
		putAndVerifyCustomer(customer, OK);
		customer = new Customer(1, null, null, 18, "", "st", "no", "zp", "ci", "co");
		putAndVerifyCustomer(customer, OK);
		customer = new Customer(1, null, null, 18, "e@x.ro", " ", "", "", "", "");
		putAndVerifyCustomer(customer, OK);
	}

	@Test
	void updateCustomerConstraintsFailed() {
		Customer customer;
		customer = new Customer(3, null, null, 18, "e@x.ro", "st", "no", "zp", "ci", "co");
		putAndVerifyCustomer(customer, OK);
		customer = new Customer(3, null, null, 18, "wrong", "st", "no", "zp", "ci", "co");
		putAndVerifyCustomer(customer, BAD_REQUEST);
	}
	@Test
	void getCustomerById() {
		getAndVerifyCustomer(CUSTOMER_ID_OK, OK)
				.jsonPath("$.id").isEqualTo(customer.getId())
				.jsonPath("$.firstName").isEqualTo(customer.getFirstName())
				.jsonPath("$.lastName").isEqualTo(customer.getLastName())
				.jsonPath("$.age").isEqualTo(customer.getAge())
				.jsonPath("$.email").isEqualTo(customer.getEmail())
				.jsonPath("$.street").isEqualTo(customer.getStreet())
				.jsonPath("$.number").isEqualTo(customer.getNumber())
				.jsonPath("$.zipCode").isEqualTo(customer.getZipCode())
				.jsonPath("$.city").isEqualTo(customer.getCity())
				.jsonPath("$.country").isEqualTo(customer.getCountry());
	}

	@Test
	void getCustomerNotFound() {
		getAndVerifyCustomer(CUSTOMER_ID_NOT_FOUND, NOT_FOUND)
				.jsonPath("$.path").isEqualTo(CUSTOMER_URL + "/" + CUSTOMER_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("NOT FOUND: " + CUSTOMER_ID_NOT_FOUND);
	}

	@Test
	void getCustomerInvalidInput() {
		getAndVerifyCustomer(CUSTOMER_ID_INVALID, UNPROCESSABLE_ENTITY)
				.jsonPath("$.path").isEqualTo(CUSTOMER_URL + "/" + CUSTOMER_ID_INVALID)
				.jsonPath("$.message").isEqualTo("Invalid customerId: " + CUSTOMER_ID_INVALID);
	}

	@Test
	void getCustomers() {
		getAndVerifyCustomers("", OK)
				.jsonPath("$[0].id").isEqualTo(customer.getId())
				.jsonPath("$[0].firstName").isEqualTo(customer.getFirstName())
				.jsonPath("$[0].lastName").isEqualTo(customer.getLastName())
				.jsonPath("$[0].age").isEqualTo(customer.getAge())
				.jsonPath("$[0].email").isEqualTo(customer.getEmail())
				.jsonPath("$[0].street").isEqualTo(customer.getStreet())
				.jsonPath("$[0].number").isEqualTo(customer.getNumber())
				.jsonPath("$[0].zipCode").isEqualTo(customer.getZipCode())
				.jsonPath("$[0].city").isEqualTo(customer.getCity())
				.jsonPath("$[0].country").isEqualTo(customer.getCountry())
				.jsonPath("$", hasSize(2));
	}

	@Test
	void searchCustomers() {
		getAndVerifyCustomers("/search", OK)
				.jsonPath("$", hasSize(2));
		getAndVerifyCustomers("/search?firstName=fn", OK)
				.jsonPath("$", hasSize(0));
		getAndVerifyCustomers("/search?lastName=ln", OK)
				.jsonPath("$", hasSize(0));
		getAndVerifyCustomers("/search?firstName=fn&lastName=ln", OK)
				.jsonPath("$", hasSize(1));
		getAndVerifyCustomers("/search?firstName=&lastName=", OK)
				.jsonPath("$", hasSize(2));
	}

	private WebTestClient.BodyContentSpec getAndVerifyCustomer(int customerId, HttpStatus expectedStatus) {
		return client.get()
				.uri(CUSTOMER_URL + "/" + customerId)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(expectedStatus)
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody();
	}

	private WebTestClient.BodyContentSpec getAndVerifyCustomers(String path, HttpStatus expectedStatus) {
		return client.get()
				.uri(CUSTOMER_URL + path)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(expectedStatus)
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody();
	}

	private void postAndVerifyCustomer(Customer customer, HttpStatus expectedStatus) {
		client.post()
				.uri(CUSTOMER_URL)
				.body(just(customer), Customer.class)
				.exchange()
				.expectStatus().isEqualTo(expectedStatus);
	}

	private void putAndVerifyCustomer(Customer customer, HttpStatus expectedStatus) {
		client.put()
				.uri(CUSTOMER_URL)
				.body(just(customer), Customer.class)
				.exchange()
				.expectStatus().isEqualTo(expectedStatus);
	}
}
package ro.veltanmarius.mkcustomer;

import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import ro.veltanmarius.mkcustomer.exceptions.InvalidInputException;
import ro.veltanmarius.mkcustomer.exceptions.ObjectNotFoundException;
import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.service.CustomerService;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class MarketingCustomerApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(MarketingCustomerApplicationTests.class);

	private static final int CUSTOMER_ID_OK = 1;
	private static final int CUSTOMER_ID_NOT_FOUND = 2;
	private static final int CUSTOMER_ID_INVALID = 3;

	@Autowired private WebTestClient client;

	@MockBean private CustomerService customerService;

	@BeforeEach
	void setUp() {
		when(customerService.getCustomer(CUSTOMER_ID_OK))
				.thenReturn(new Customer(CUSTOMER_ID_OK, "fn", "ln", 18, "e@x.ro", "st", "no", "zp", "ci", "co"));

		when(customerService.getCustomer(CUSTOMER_ID_NOT_FOUND))
				.thenThrow(new ObjectNotFoundException("NOT FOUND: " + CUSTOMER_ID_NOT_FOUND));

		when(customerService.getCustomer(CUSTOMER_ID_INVALID))
				.thenThrow(new InvalidInputException("INVALID: " + CUSTOMER_ID_INVALID));
	}

	@Test
	void contextLoads() {
		LOG.debug("contextLoads: Tests are configured correctly");
	}

	@Test
	void createCustomerOK() {
		Customer customer;
		customer = new Customer(1, "fn", "ln", 18, "e@x.ro", "st", "no", "zp", "ci", "co");
		postAndVerifyProduct(customer, OK);
		customer = new Customer(1, "fn ", "ln ", 18, "", "st", "no", "zp", "ci", "co");
		postAndVerifyProduct(customer, OK);
		customer = new Customer(1, " FN", " LN", 18, "e@x.ro", " ", "", "", "", "");
		postAndVerifyProduct(customer, OK);
	}

	@Test
	void createCustomerMandatoryFieldsMissing() {
		Customer customer;
		customer = new Customer(2, " ", " ", 18, "", "", "", "", "", "");
		postAndVerifyProduct(customer, BAD_REQUEST);
		customer = new Customer(2, "fn", " ", 18, "", "", "", "", "", "");
		postAndVerifyProduct(customer, BAD_REQUEST);
		customer = new Customer(2, "fn", "ln", 18, "", "", "", "", "", "");
		postAndVerifyProduct(customer, UNPROCESSABLE_ENTITY);
		customer = new Customer(2, "fn", "ln", 18, "", "st", "", "", "", "");
		postAndVerifyProduct(customer, UNPROCESSABLE_ENTITY);
		customer = new Customer(2, "fn", "ln", 18, "", "st", "no", "", "", "");
		postAndVerifyProduct(customer, UNPROCESSABLE_ENTITY);
		customer = new Customer(2, "fn", "ln", 18, "", "st", "no", "zp", "", "");
		postAndVerifyProduct(customer, UNPROCESSABLE_ENTITY);
		customer = new Customer(2, "fn", "ln", 18, "", "st", "no", "zp", "ci", "");
		postAndVerifyProduct(customer, UNPROCESSABLE_ENTITY);
	}

	@Test
	void createCustomerConstraintsFailed() {
		Customer customer;
		customer = new Customer(1, "fn", "ln", 18, "e@x.ro", "st", "no", "zp", "ci", "co");
		postAndVerifyProduct(customer, OK);
		customer = new Customer(1, "fn", "ln", 18, "blabla", "st", "no", "zp", "ci", "co");
		postAndVerifyProduct(customer, BAD_REQUEST);
		customer = new Customer(1, "fn", "ln", 17, "e@x.ro", "st", "no", "zp", "ci", "co");
		postAndVerifyProduct(customer, BAD_REQUEST);
		customer = new Customer(1, "fn", "ln", -1, "e@x.ro", "st", "no", "zp", "ci", "co");
		postAndVerifyProduct(customer, BAD_REQUEST);
	}

	@Test
	void updateCustomerOK() {
		Customer customer;
		customer = new Customer(1, "fn", "ln", 18, "e@x.ro", "st", "no", "zp", "ci", "co");
		puAndVerifyProduct(customer, OK);
		customer = new Customer(1, "fn ", "ln ", 18, "", "st", "no", "zp", "ci", "co");
		puAndVerifyProduct(customer, OK);
		customer = new Customer(1, " FN", " LN", 18, "e@x.ro", " ", "", "", "", "");
		puAndVerifyProduct(customer, OK);
	}
	@Test
	void getCustomerById() {
		getAndVerifyProduct(CUSTOMER_ID_OK, OK)
				.jsonPath("$.id").isEqualTo(CUSTOMER_ID_OK);
	}

	@Test
	void getCustomerNotFound() {
		getAndVerifyProduct(CUSTOMER_ID_NOT_FOUND, NOT_FOUND)
				.jsonPath("$.path").isEqualTo("/marketing/customers/" + CUSTOMER_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("NOT FOUND: " + CUSTOMER_ID_NOT_FOUND);
	}

	@Test
	void getCustomerInvalidInput() {
		getAndVerifyProduct(CUSTOMER_ID_INVALID, UNPROCESSABLE_ENTITY)
				.jsonPath("$.path").isEqualTo("/marketing/customers/" + CUSTOMER_ID_INVALID)
				.jsonPath("$.message").isEqualTo("INVALID: " + CUSTOMER_ID_INVALID);
	}

	private WebTestClient.BodyContentSpec getAndVerifyProduct(int customerId, HttpStatus expectedStatus) {
		return client.get()
				.uri("/marketing/customers/" + customerId)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(expectedStatus)
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody();
	}

	private void postAndVerifyProduct(Customer customer, HttpStatus expectedStatus) {
		client.post()
				.uri("/marketing/customers")
				.body(just(customer), Customer.class)
				.exchange()
				.expectStatus().isEqualTo(expectedStatus);
	}

	private void puAndVerifyProduct(Customer customer, HttpStatus expectedStatus) {
		client.put()
				.uri("/marketing/customers")
				.body(just(customer), Customer.class)
				.exchange()
				.expectStatus().isEqualTo(expectedStatus);
	}
}
package ro.veltanmarius.mkcustomer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Marius Veltan
 */
@SpringBootApplication
public class MarketingCustomerApplication {

	private static final Logger LOG = LoggerFactory.getLogger(MarketingCustomerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MarketingCustomerApplication.class, args);
		LOG.info("MarketingCustomerApplication started! ");
	}

}
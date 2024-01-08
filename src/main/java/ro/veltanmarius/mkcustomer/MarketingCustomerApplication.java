package ro.veltanmarius.mkcustomer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Marius Veltan
 */
@SpringBootApplication
@Slf4j
public class MarketingCustomerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MarketingCustomerApplication.class, args);
		log.info("MarketingCustomerApplication started! ");
	}

}
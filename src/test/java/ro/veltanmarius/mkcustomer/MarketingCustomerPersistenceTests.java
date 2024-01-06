package ro.veltanmarius.mkcustomer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import ro.veltanmarius.mkcustomer.model.entity.CustomerEntity;
import ro.veltanmarius.mkcustomer.repository.CustomerRepository;

/**
 * @author Marius Veltan
 */
@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MarketingCustomerPersistenceTests {

    @Autowired
    private CustomerRepository repository;

    private CustomerEntity savedEntity;

    @BeforeEach
    void setupDb() {
        repository.deleteAll();

        CustomerEntity entity = new CustomerEntity(1, "firstName", "lastName", 18, "email@email.com", "st", "no", "zp", "ci", "co");
        savedEntity = repository.save(entity);

        assertEqualsReview(entity, savedEntity);
    }

    @Test
    void create() {
        CustomerEntity newEntity = new CustomerEntity(1, "firstName", "lastName", 18, "email@email.com", "st", "no", "zp", "ci", "co");
        repository.save(newEntity);

        CustomerEntity foundEntity = repository.findById(newEntity.getId()).get();
        assertEqualsReview(newEntity, foundEntity);

        assertEquals(2, repository.count());
    }

    @Test
    void update() {
        savedEntity.setEmail("contact@email.com");
        repository.save(savedEntity);

        CustomerEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long) foundEntity.getVersion());
        assertEquals("contact@email.com", foundEntity.getEmail());
    }

    @Test
    void getByCustomerId() {
        Optional<CustomerEntity> entities = repository.findById(savedEntity.getId());

        assertTrue(entities.isPresent());
        assertEqualsReview(savedEntity, entities.get());
    }

    @Test
    void getCustomerByFirstNameOrLastName() {
        List<CustomerEntity> entities = repository.findCustomerByFirstNameOrLastName(savedEntity.getFirstName(), savedEntity.getLastName());

        assertThat(entities, hasSize(1));
        assertEqualsReview(savedEntity, entities.get(0));
    }

    @Test
    void getCustomerOnlyByFirstName() {
        List<CustomerEntity> entities = repository.findCustomerByFirstNameOrLastName(savedEntity.getFirstName(), "");

        assertThat(entities, hasSize(1));
        assertEqualsReview(savedEntity, entities.get(0));
    }

    @Test
    void getCustomerOnlyByLastName() {
        List<CustomerEntity> entities = repository.findCustomerByFirstNameOrLastName("", savedEntity.getLastName());

        assertThat(entities, hasSize(1));
        assertEqualsReview(savedEntity, entities.get(0));
    }

    @Test
    void searchCustomerByFirstNameOrLastName() {
        // IgnoreCase
        List<CustomerEntity> entities = repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("NAME", "NAME");
        assertThat(entities, hasSize(1));
        assertEqualsReview(savedEntity, entities.get(0));
        // Start With
        entities = repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("first", "x");
        assertThat(entities, hasSize(1));
        assertEqualsReview(savedEntity, entities.get(0));
        // End With
        entities = repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("x", "me");
        assertThat(entities, hasSize(1));
        assertEqualsReview(savedEntity, entities.get(0));
        // Containing
        entities = repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("irstN", null);
        assertThat(entities, hasSize(1));
        assertEqualsReview(savedEntity, entities.get(0));
        // None
        entities = repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("x", "y");
        assertThat(entities, hasSize(0));
        entities = repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(null, "y");
        assertThat(entities, hasSize(0));
        entities = repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("x", null);
        assertThat(entities, hasSize(0));
    }

    @Test
    void optimisticLockError() {
        // Store the saved entity in two separate entity objects
        CustomerEntity entity1 = repository.findById(savedEntity.getId()).get();
        CustomerEntity entity2 = repository.findById(savedEntity.getId()).get();

        // Update the entity using the first entity object
        entity1.setFirstName("n1");
        repository.save(entity1);

        // Update the entity using the second entity object.
        // This should fail since the second entity now holds an old version number, i.e. an Optimistic Lock Error
        assertThrows(OptimisticLockingFailureException.class, () -> {
            entity2.setFirstName("n2");
            repository.save(entity2);
        });

        // Get the updated entity from the database and verify its new state
        CustomerEntity updatedEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (int)updatedEntity.getVersion());
        assertEquals("n1", updatedEntity.getFirstName());
    }

    private void assertEqualsReview(CustomerEntity expectedEntity, CustomerEntity actualEntity) {
        assertEquals(expectedEntity.getId(),        actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),   actualEntity.getVersion());
        assertEquals(expectedEntity.getFirstName(), actualEntity.getFirstName());
        assertEquals(expectedEntity.getLastName(),  actualEntity.getLastName());
        assertEquals(expectedEntity.getAge(),       actualEntity.getAge());
        assertEquals(expectedEntity.getEmail(),     actualEntity.getEmail());
        assertEquals(expectedEntity.getStreet(),    actualEntity.getStreet());
        assertEquals(expectedEntity.getNumber(),    actualEntity.getNumber());
        assertEquals(expectedEntity.getZipCode(),   actualEntity.getZipCode());
        assertEquals(expectedEntity.getCity(),      actualEntity.getCity());
        assertEquals(expectedEntity.getCountry(),   actualEntity.getCountry());
    }
}
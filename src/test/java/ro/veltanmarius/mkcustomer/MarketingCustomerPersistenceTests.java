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
        Optional<CustomerEntity> entity = repository.findById(savedEntity.getId());

        assertTrue(entity.isPresent());
        assertEqualsReview(savedEntity, entity.get());
    }

    @Test
    void getCustomerByFirstNameAndLastName() {
        List<CustomerEntity> entity = repository.findCustomerByFirstNameOrLastName(savedEntity.getFirstName(), savedEntity.getLastName());

        assertThat(entity, hasSize(1));
        assertEqualsReview(savedEntity, entity.get(0));
    }

    @Test
    void getCustomerOnlyByFirstName() {
        List<CustomerEntity> entity = repository.findCustomerByFirstNameOrLastName(savedEntity.getFirstName(), "");

        assertThat(entity, hasSize(1));
        assertEqualsReview(savedEntity, entity.get(0));
    }

    @Test
    void getCustomerOnlyByLastName() {
        List<CustomerEntity> entity = repository.findCustomerByFirstNameOrLastName("", savedEntity.getLastName());

        assertThat(entity, hasSize(1));
        assertEqualsReview(savedEntity, entity.get(0));
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

        // Get the updated entity from the database and verify its new sate
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
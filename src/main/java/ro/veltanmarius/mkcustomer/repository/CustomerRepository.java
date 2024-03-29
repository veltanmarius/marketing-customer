package ro.veltanmarius.mkcustomer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.veltanmarius.mkcustomer.model.entity.CustomerEntity;

import java.util.List;

/**
 * @author Marius Veltan
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    List<CustomerEntity> findCustomerByFirstNameOrLastName(String firstName, String lastName);

    List<CustomerEntity> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customers, Long> {
}
